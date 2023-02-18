import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

fun Any.objectToJson(): String = jacksonObjectMapper().writer().writeValueAsString(this)

fun <T> String.jsonToObject(type: Class<T>, rootName: String): T {
    val mapper = jacksonObjectMapper().reader().withRootName(rootName)
    return mapper.readValue(this, type)
}