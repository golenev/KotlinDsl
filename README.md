Проект для применения Kotlin DSL на примере псевдо http клиента, 
который позволяет отправлять запросы, используя подобный синтаксис
```
    val postRequestEntity = requestExecutor {
        POST withBody productBody withHeaders headers withParams params
    }
```
