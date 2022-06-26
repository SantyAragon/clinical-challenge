package com.lastdance.clinical.utils;

import net.bytebuddy.utility.RandomString;

import java.util.HashSet;
import java.util.Set;

public final class Pacienteutils {
    private static Set<String> tokensGenerados = new HashSet<>();

    public static String generarToken() {
        String token;
        do {
            token = RandomString.make(48);
        } while (tokensGenerados.contains(token));
        tokensGenerados.add(token);
        return token;
    }
}
