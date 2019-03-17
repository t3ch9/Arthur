package com.codebrig.omnisrc.generator.local

import com.codebrig.omnisrc.SourceLanguage
import com.codebrig.omnisrc.generator.SchemaGenerator
import com.codebrig.omnisrc.observe.ObservationConfig
import com.codebrig.omnisrc.observe.ObservedLanguage
import com.codebrig.omnisrc.observe.ObservedLanguages
import com.codebrig.omnisrc.schema.SchemaSegment
import com.codebrig.omnisrc.schema.SegmentedSchemaConfig
import com.codebrig.omnisrc.schema.grakn.GraknSchemaWriter

import java.util.concurrent.TimeUnit

/**
 * Generate a multi-language schema by observing local source code
 *
 * @version 0.3
 * @since 0.2
 * @author <a href="mailto:brandon.fergerson@codebrig.com">Brandon Fergerson</a>
 */
class OmnilingualLocalSchemaGenerator extends SchemaGenerator {

    static void main(String[] args) {
        def inputDirectory = new File(args[0])
        long startTime = System.currentTimeMillis()
        def schemaGenerator = new SchemaGenerator(ObservationConfig.fullStructure())
        def observedLanguages = new ArrayList<ObservedLanguage>()
        SourceLanguage.values().each {
            if (it != SourceLanguage.OmniSRC) {
                observedLanguages.add(schemaGenerator.observeLanguage(it, inputDirectory))
            }
        }

        println "Merging common language features"
        def omniLanguage = ObservedLanguages.mergeLanguages(observedLanguages)

        println "Writing segmented Grakn schema"
        def schemaWriter = new GraknSchemaWriter(omniLanguage, observedLanguages.toArray(new ObservedLanguage[0]))
        schemaWriter.storeSegmentedSchemaDefinition(new SegmentedSchemaConfig()
                .withFileSegment(new File("src/main/resources/schema/omnilingual/",
                "OmniSRC_" + SourceLanguage.OmniSRC.qualifiedName + "_Base_Structure.gql"), ObservationConfig.baseStructure().asArray())
                .withFileSegment(new File("src/main/resources/schema/omnilingual/",
                "OmniSRC_" + SourceLanguage.OmniSRC.qualifiedName + "_Individual_Semantic_Roles.gql"), SchemaSegment.INDIVIDUAL_SEMANTIC_ROLES)
                .withFileSegment(new File("src/main/resources/schema/omnilingual/",
                "OmniSRC_" + SourceLanguage.OmniSRC.qualifiedName + "_Actual_Semantic_Roles.gql"), SchemaSegment.ACTUAL_SEMANTIC_ROLES))
        println "Completed in: " + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime) + "s"
    }
}