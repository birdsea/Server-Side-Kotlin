package io.github.birdsea.ServerSideKotlin.component

/**
 * Aopクラス
 */

import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
object Aop {
    @Before("execution(* *..*Controller.*(..))")
    fun before() {
    }

    @After("execution(* *..*Controller.*(..))")
    fun after() {
    }
}