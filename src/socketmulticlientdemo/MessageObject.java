/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketmulticlientdemo;

import java.io.Serializable;


public class MessageObject implements Serializable {

    public String message;

    public MessageObject(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
