package com.example.rabbitdemo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageBuilder



@RestController
@RequestMapping("/api/v1")
class TestController {

    @Autowired
    RabbitTemplate rabbitTemplate


    /*@GetMapping("/test/{name}")
    String testAPI(@PathVariable("name") String name) {

        Person p = new Person(id: 1l, name: name)
        rabbitTemplate.convertAndSend("Mobile", p)
        rabbitTemplate.convertAndSend("Direct-exchange", "register", p)
        rabbitTemplate.convertAndSend("Fanout-Exchange", "", p)
        rabbitTemplate.convertAndSend("Topic-Exchange", "register.person.add", p)
        return "Success"
    }*/

    @GetMapping("/test/{name}")
    String testAPI(@PathVariable("name") String name) {

        Person p = new Person(id: 1l, name: name)

        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ObjectOutput out = new ObjectOutputStream(baos)
        out.writeObject(p)
        out.flush()
        out.close()

        byte[] byteMessage = baos.toByteArray()
        baos.close()

        Message message = MessageBuilder.withBody(byteMessage).setHeader("sev","sev1").setHeader("pri","p1").build()


        rabbitTemplate.send("Headers-Exchange", "", message)

        return "Success"
    }
}