package strings

data class Human(var name: String, var age: Int, var salary: Double, var id: Long)

class DataBase {

    val dataBase: MutableList<Human> = mutableListOf()

    fun putHuman(name: String, age: Int, salary: Double, id: Long) {
        dataBase.add(Human(name, age, salary, id))
    }

    private fun putHuman(human: Human): Human {
        return Human(human.name, human.age, human.salary, human.id);
    }

    operator fun invoke(function: DataBase.() -> Unit): Human {
        function(this)
        return dataBase.last()
    }

    infix fun String.age(age: Int): Human {
        return Human(this, age, 0.0, 0)
    }

    infix fun Human.salary(salary: Double): Human {
        return Human(this.name, this.age, salary, this.id)
    }

    infix fun Human.id(id: Long): Human {
        return Human(this.name, this.age, this.salary, id).also { dataBase.add(putHuman(it)) }
    }
}

fun main() {
    val dataBase = DataBase()

    dataBase {
        "Ivan" age 12 salary 365.9 id 9789774343
        "Dima" age 25 salary 1545.9 id 345345343
        "Ivan" age 35 salary 2955.9 id 123235423343
    }

    println(dataBase.dataBase)
}