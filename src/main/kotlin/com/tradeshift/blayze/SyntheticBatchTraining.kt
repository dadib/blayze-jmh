package com.tradeshift.blayze

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
open class SyntheticBatchTraining {

    @Param("1000", "100000")
    var batchSize: Int = 0

    //@Param("1", "20")
    var nFeatures: Int = 1

    @Param("2", "20", "20000")
    var nClasses: Int = 0

    @Param("100000", "1000")
    var nFeatureValues: Int = 0

    //@Param("100000", "1000000")
    var nUpdates: Int = 1000_000

    @Benchmark
    fun train() {
        val model = Model()
        UpdateGenerator(321L)
                .generate(nFeatures, nFeatureValues, nClasses)
                .take(nUpdates)
                .chunked(batchSize)
                .fold(model, { m, updates -> m.batchAdd(updates) })
    }
}