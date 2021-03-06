// SKIP_TXT
// WITH_RUNTIME

import kotlin.coroutines.experimental.*

interface Job : CoroutineContext.Element {}
interface Deferred<out T> : Job {
    suspend fun await(): T
}
fun <T> async(<!UNUSED_PARAMETER!>block<!>: suspend () -> T): Deferred<T> = TODO()

suspend fun fib(n: Long) =
    async {
        when {
            n < 2 -> n
            else -> <!TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM!>fib(n - 1)<!>.<!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>await<!>() <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>+<!> fib(n - 2).<!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>await<!>()
        }
    }