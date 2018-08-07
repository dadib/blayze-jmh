package com.tradeshift.blayze

import com.tradeshift.blayze.dto.Update
import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
open class NewsgroupBatchTraining {

    @Param("10", "1000", "10000")
    var batchSize: Int = 0

    var updates: List<Update> = newsgroup("20newsgroup_train.txt")

    @Benchmark
    fun trainNewsgroupBatches() {
        val model = Model()
        updates.chunked(batchSize).fold(model, { m, updates -> m.batchAdd(updates) })
    }
}


