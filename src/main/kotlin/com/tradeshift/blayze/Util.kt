package com.tradeshift.blayze

import com.tradeshift.blayze.dto.Inputs
import com.tradeshift.blayze.dto.Update
import java.util.*

fun newsgroup(fname: String): List<Update> {
    val lines = ClassLoader.getSystemResource(fname).readText(Charsets.UTF_8).split("\n")
    val updates = mutableListOf<Update>()

    for (line in lines) {
        val split = line.split(" ".toRegex(), 2).toTypedArray()
        val outcome = split[0]
        var f = Inputs()
        if (split.size == 2) { //some are legit empty
            f = Inputs(text = mapOf("q" to split[1]))
        }
        updates.add(Update(f, outcome))
    }
    return updates
}


class UpdateGenerator(seed: Long) {

    private val random = Random(seed)

    fun generate(nFeatures: Int, nFeatureValues: Int, nClasses: Int, nFeaturesPerUpdate: Int = 1): Sequence<Update> {
        return generateSequence {
            val featureName = random.nextInt(nFeatures).toString()
            val features = (0 until nFeaturesPerUpdate)
                    .map { random.nextInt(nFeatureValues).toString() }
                    .joinToString(separator = " ")

            val input = Inputs(text = mapOf(featureName to features))
            val outcome = random.nextInt(nClasses).toString()
            Update(input, outcome)
        }
    }
}