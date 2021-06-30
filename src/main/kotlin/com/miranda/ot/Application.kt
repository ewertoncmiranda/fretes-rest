package com.miranda.ot

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("com.miranda.ot")
		.start()
}

