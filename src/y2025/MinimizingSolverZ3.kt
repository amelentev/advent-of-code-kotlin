package y2025

import com.microsoft.z3.Context
import com.microsoft.z3.Expr
import com.microsoft.z3.Optimize
import io.ksmt.expr.KExpr
import io.ksmt.solver.KModel
import io.ksmt.solver.KSolver
import io.ksmt.solver.z3.*
import io.ksmt.sort.KArithSort
import java.lang.reflect.Method

data class MinimizingSolverZ3(val impl: KZ3Solver) : KSolver<KZ3SolverConfiguration> by impl {
    val z3Ctx: KZ3Context =
        impl::class.java.getDeclaredField("z3Ctx").apply { isAccessible = true }.get(impl) as KZ3Context
    val exprInternalizer = impl::class.java.getDeclaredMethod("getExprInternalizer")
        .apply { isAccessible = true }.invoke(impl) as KZ3ExprInternalizer
    val exprCreate = Expr::class.java.getDeclaredMethod("create", Context::class.java, Long::class.java)
        .apply { isAccessible = true } as Method

    fun <K : KArithSort> modelMinimizing(
        expr: KExpr<K>,
    ): KModel {
        // Sadly, we need to create an Optimize object and copy all Solver constraints to it
        val opt: Optimize = z3Ctx.nativeContext.mkOptimize()
        //applyTimeout(opt, timeout)
        for (c in impl.nativeSolver().assertions) opt.Assert(c)

        // Add the expression to minimize
        val exprId = with(exprInternalizer) { expr.internalizeExpr() }
        val convertedExpr = exprCreate.invoke(null, z3Ctx.nativeContext, exprId) as Expr<*>
        opt.MkMinimize(convertedExpr)

        // Normal check, but using the optimizer
        opt.Check()

        // Extract the model and return it
        return KZ3Model(opt.model, expr.ctx, z3Ctx, exprInternalizer)
    }
}