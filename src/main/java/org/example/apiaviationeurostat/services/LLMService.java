package org.example.apiaviationeurostat.services;

import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LLMService {

    private final OllamaChatModel chatModel;

    public LLMService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    // 1. Definimos la estructura exacta que queremos extraer de la frase del usuario.
    // Usamos un 'record' de Java para que sea rápido y limpio.
    public record FiltroIA(String geo, String timeStart, String timeEnd, Integer minPassengers) {}

    public FiltroIA extraerFiltrosDeTexto(String fraseUsuario) {

        // 2. Instanciamos el conversor que "traducirá" el JSON de la IA a nuestro objeto Java
        var outputConverter = new BeanOutputConverter<>(FiltroIA.class);

        // 3. Diseñamos el Prompt (las instrucciones estrictas para Llama 3)
        String textoPrompt = """
                Eres un asistente experto en bases de datos de aviación europea.
                Tu trabajo es leer la consulta del usuario en lenguaje natural y extraer los parámetros de búsqueda.
                
                Reglas estrictas de extracción:
                - 'geo': Extrae el país y conviértelo a su código oficial de 2 letras (ej: España = ES, Francia = FR, Alemania = DE). Si dice 'Europa' o 'Unión Europea', pon 'EU27_2020'.
                - 'timeStart': Mes de inicio en formato YYYY-MM (ej: enero de 2024 = 2024-01).
                - 'timeEnd': Mes de fin en formato YYYY-MM.
                - 'minPassengers': Número entero con la cantidad mínima de pasajeros.
                
                Si el usuario no especifica alguno de los datos en su frase, debes dejar ese campo como null.
                NO escribas ninguna otra palabra, solo el formato requerido.
                
                Consulta del usuario: {consulta}
                
                {formato}
                """;

        // 4. Inyectamos las variables en la plantilla del prompt
        PromptTemplate promptTemplate = new PromptTemplate(textoPrompt);
        Prompt prompt = promptTemplate.create(
                Map.of(
                        "consulta", fraseUsuario,
                        "formato", outputConverter.getFormat() // Esto le inyecta las instrucciones del JSON automáticamente
                ),
                OllamaChatOptions.builder()
                        .model("llama3")
                        .temperature(0.0) // MUY IMPORTANTE: Temperatura 0 para que sea analítico y no invente datos
                        .build()
        );

        // 5. Llamamos a Llama 3 en tu Docker local
        Generation generation = chatModel.call(prompt).getResult();

        // 6. Convertimos el texto crudo que devuelve la IA en nuestro objeto FiltroIA
        return outputConverter.convert(generation.getOutput().getText());
    }
}
