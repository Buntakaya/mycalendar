package example.com.mycalendar.dto

data class ScheduleDto(val id:Long, var date:String, var time:String, var title: String, var detail:String){
    override fun toString(): String = """$time ${this.title}"""
}
