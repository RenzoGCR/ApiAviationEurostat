package org.example.apiaviationeurostat.controllers;

import org.example.apiaviationeurostat.services.AviationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.example.apiaviationeurostat.dto.AviationFilterDTO;
import org.example.apiaviationeurostat.exceptions.InvalidRequestException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class AviationWebController {

    private final AviationService aviationService;

    public AviationWebController(AviationService aviationService) {
        this.aviationService = aviationService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("totalVuelos", aviationService.getAllRecords().size());
        return "index";
    }

    @GetMapping("/vuelos")
    public String mostrarVuelos(Model model) {
        var listaVuelos = aviationService.getAllRecords();
        model.addAttribute("vuelos", listaVuelos);
        model.addAttribute("utils", this);
        return "lista_vuelos";
    }

    @GetMapping("/buscar")
    public String mostrarFormularioBusqueda(Model model) {
        // Le pasamos a la vista un objeto DTO vacío para que el formulario lo rellene
        model.addAttribute("filtro", new AviationFilterDTO());
        return "busqueda"; // Nombre del nuevo archivo HTML
    }

    @PostMapping("/buscar")
    public String procesarBusqueda(@ModelAttribute("filtro") AviationFilterDTO filtro, Model model) {
        try {
            // Llamamos al servicio de búsqueda con la validación de fechas
            var resultados = aviationService.getByAdvancedFilter(filtro);

            // Pasamos los resultados a la vista
            model.addAttribute("vuelos", resultados);
            model.addAttribute("mensajeExito", "Búsqueda completada. Se encontraron " + resultados.size() + " registros.");

        } catch (InvalidRequestException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "busqueda";
    }

    @GetMapping("/vuelos/pais/{geo}")
    public String detallePais(@PathVariable String geo, Model model) {
        try {
            var vuelosPais = aviationService.getByCountry(geo);
            model.addAttribute("vuelos", vuelosPais);
            model.addAttribute("nombrePais", geo); // Las siglas (ej: AT)

            // NUEVO: Añadimos el nombre completo y el código para la bandera
            model.addAttribute("nombreCompleto", obtenerNombrePais(geo));
            model.addAttribute("codigoBandera", obtenerCodigoBandera(geo));

            return "detalle_pais";
        } catch (Exception e) {
            model.addAttribute("error", "No se encontraron datos para el país: " + geo);
            return "lista_vuelos";
        }
    }

    @GetMapping("/vuelos/top")
    public String mostrarTopVuelos(Model model) {
        // Buscamos vuelos con más de 1.000.000 de pasajeros
        var topVuelos = aviationService.getTopPassengers(1000000L);
        model.addAttribute("vuelos", topVuelos);
        model.addAttribute("tituloPie", "Mostrando vuelos con más de 1 millón de pasajeros");
        model.addAttribute("utils", this);
        return "lista_vuelos";
    }

    // --- MÉTODOS DE AYUDA PARA LA UX ---
    public String obtenerNombrePais(String geo) {
        java.util.Map<String, String> paises = java.util.Map.ofEntries(
                // Países de la UE que ya tenías
                java.util.Map.entry("AT", "Austria"), java.util.Map.entry("BE", "Bélgica"),
                java.util.Map.entry("BG", "Bulgaria"), java.util.Map.entry("CY", "Chipre"),
                java.util.Map.entry("CZ", "República Checa"), java.util.Map.entry("DE", "Alemania"),
                java.util.Map.entry("DK", "Dinamarca"), java.util.Map.entry("EE", "Estonia"),
                java.util.Map.entry("EL", "Grecia"), java.util.Map.entry("ES", "España"),
                java.util.Map.entry("FI", "Finlandia"), java.util.Map.entry("FR", "Francia"),
                java.util.Map.entry("HR", "Croacia"), java.util.Map.entry("HU", "Hungría"),
                java.util.Map.entry("IE", "Irlanda"), java.util.Map.entry("IT", "Italia"),
                java.util.Map.entry("LT", "Lituania"), java.util.Map.entry("LU", "Luxemburgo"),
                java.util.Map.entry("LV", "Letonia"), java.util.Map.entry("MT", "Malta"),
                java.util.Map.entry("NL", "Países Bajos"), java.util.Map.entry("PL", "Polonia"),
                java.util.Map.entry("PT", "Portugal"), java.util.Map.entry("RO", "Rumanía"),
                java.util.Map.entry("SE", "Suecia"), java.util.Map.entry("SI", "Eslovenia"),
                java.util.Map.entry("SK", "Eslovaquia"), java.util.Map.entry("UK", "Reino Unido"),

                // NUEVOS: Países EFTA y Candidatos (Eurostat)
                java.util.Map.entry("CH", "Suiza"),
                java.util.Map.entry("BA", "Bosnia y Herzegovina"),
                java.util.Map.entry("NO", "Noruega"),
                java.util.Map.entry("IS", "Islandia"),
                java.util.Map.entry("LI", "Liechtenstein"),
                java.util.Map.entry("ME", "Montenegro"),
                java.util.Map.entry("MK", "Macedonia del Norte"),
                java.util.Map.entry("AL", "Albania"),
                java.util.Map.entry("RS", "Serbia"),
                java.util.Map.entry("TR", "Turquía"),

                java.util.Map.entry("EU27_2020", "Unión Europea (27 países)")
        );
        return paises.getOrDefault(geo, "País Desconocido (" + geo + ")");
    }

    public String obtenerCodigoBandera(String geo) {
        // Eurostat usa algunas siglas distintas al estándar ISO de banderas. Las corregimos:
        if (geo.equals("EL")) return "gr"; // Grecia
        if (geo.equals("UK")) return "gb"; // Reino Unido
        if (geo.equals("EU27_2020")) return "eu";
        return geo.toLowerCase(); // FlagCDN requiere minúsculas (ej: "es", "at")
    }
}