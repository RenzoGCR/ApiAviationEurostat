# ✈️ Aviation Eurostat - Web Dashboard & AI Assistant

Plataforma web desarrollada con **Spring Boot** para consultar y analizar estadísticas de pasajeros aéreos en Europa (basada en datos oficiales de Eurostat). El proyecto cuenta con un panel de control interactivo, búsquedas avanzadas protegidas mediante autenticación, y un **asistente de Inteligencia Artificial** con reconocimiento de voz.

## ✨ Características Principales

* **📊 Dashboard Interactivo:** Visualización de datos y gráficas de tendencias generadas con Chart.js.
* **🔐 Seguridad:** Búsqueda avanzada protegida mediante credenciales de administrador (Spring Security).
* **🤖 Asistente IA Integrado:** Búsqueda en lenguaje natural ("Traducción" de texto a filtros de base de datos) usando un modelo **Llama 3** ejecutado en local.
* **🎤 Búsqueda por Voz:** Integración con la Web Speech API para dictar las búsquedas al asistente de IA sin usar el teclado.
* **💎 Diseño Premium:** Interfaz moderna en Modo Oscuro implementando la tendencia *Glassmorphism* (Morfismo de cristal) para una experiencia de usuario inmersiva.

## 📚 Tecnologías

**Backend:**
* **Java 17**
* **Spring Boot 3.4.2**
* **Spring Data MongoDB** (Persistencia en la nube)
* **Spring Security** (Autenticación y Autorización)
* **Spring AI** (Integración nativa con LLMs)

**Frontend:**
* **Thymeleaf** (Motor de plantillas)
* **Bootstrap 5 & CSS3** (Diseño Glassmorphism)
* **Chart.js** (Gráficos)
* **Web Speech API** (Reconocimiento de voz nativo del navegador)

**Infraestructura:**
* **MongoDB Atlas** (Base de datos remota)
* **Docker & Ollama** (Contenedor local para el modelo Llama 3)
* **Maven** (Gestor de dependencias)

## ⚙️ Configuración e Instalación

### 1. Requisitos previos
* Java JDK 17+ instalado.
* Cuenta en MongoDB Atlas (o instancia local).
* **Docker Desktop** instalado y en ejecución (requerido para la IA).

### 2. Variables de Entorno
El proyecto utiliza variables en `application.properties` para no exponer credenciales. Debes configurar:

* `MONGODB_URI`: Tu cadena de conexión a MongoDB Atlas.
    * *Ejemplo:* `mongodb+srv://usuario:password@cluster.mongodb.net/eurostat_data`

### 3. Configuración de Llama 3 (Inteligencia Artificial)
Para que la búsqueda natural funcione, necesitas tener el modelo ejecutándose en segundo plano:
1. Abre tu terminal e inicia un contenedor de Ollama.
2. Descarga y ejecuta el modelo Llama 3:
   ```bash
   docker exec -it <nombre_de_tu_contenedor_ollama> ollama run llama3