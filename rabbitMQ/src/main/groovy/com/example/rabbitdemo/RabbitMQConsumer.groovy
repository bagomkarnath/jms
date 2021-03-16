package com.example.rabbitdemo

import org.springframework.stereotype.Service
import org.springframework.amqp.rabbit.annotation.RabbitListener

@Service
class RabbitMQConsumer {

    /*@RabbitListener(queues = "Mobile")
    def getMessage(Person p) {
        println(p.name)
    }*/

    @RabbitListener(queues = "register")
    def getMessage(byte[] message) {
        ByteArrayInputStream bais = new ByteArrayInputStream(message)
        ObjectInput oi = new ObjectInputStream(bais)
        Person p = (Person) oi.readObject()
        oi.close()
        bais.close()
        println p.getName()

    }
}