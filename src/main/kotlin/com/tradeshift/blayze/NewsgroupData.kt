package com.tradeshift.blayze

import com.tradeshift.blayze.dto.Inputs
import com.tradeshift.blayze.dto.Update
import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
open class NewsgroupData {

    var model: Model = Model()

    var nextUpdate = Update(Inputs(), "")

    @Setup
    fun setup() {
        val ng = newsgroup("20newsgroup_train.txt")
        val pastUpdates = ng.subList(0, ng.size - 2)
        model = Model().batchAdd(pastUpdates)
        nextUpdate = ng.last()
    }

    @Benchmark
    fun update() {
        model.add(nextUpdate)
    }

    @Benchmark
    fun predict() {
        model.predict(nextUpdate.inputs)
    }
}