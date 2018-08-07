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
open class SyntheticData {

    //@Param("1000000", "10000")
    var nPastUpdates: Int = 1000_000

    //@Param("1", "20")
    var nFeatures: Int = 1

    @Param("2", "20", "20000")
    var nClasses: Int = 0

    @Param("100000", "1000")
    var nFeatureValues: Int = 0

    @Param("1", "10", "1000")
    var nFeatureValuesPerUpdate: Int = 0

    var model: Model = Model()

    var nextUpdate: Update = Update(Inputs(), "")

    @Setup
    fun setup() {
        val updates = UpdateGenerator(123L)
        val pastUpdates = updates.generate(nFeatures, nFeatureValues, nClasses).take(nPastUpdates).toList()
        model = Model().batchAdd(pastUpdates)
        nextUpdate = updates.generate(nFeatures, nFeatureValues, nClasses, nFeatureValuesPerUpdate).first()
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