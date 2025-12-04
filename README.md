# 🧮 Calculadora de Nómina
> Proyecto de práctica con enfoque en **Desarrollo Guiado por Pruebas (TDD)** utilizando **Java** y **JUnit**

---

## 📘 Descripción del Proyecto

Este proyecto consiste en el desarrollo de una **calculadora de nómina** en **Java**, cuyo objetivo es automatizar el cálculo del salario neto de los empleados.  
Se tendrán en cuenta factores como:

- Sueldo base  
- Horas trabajadas  
- Deducciones (ISR, seguridad social, etc.)  
- Bonificaciones y retenciones adicionales  

El enfoque principal del proyecto no es únicamente la funcionalidad, sino la **aplicación práctica del enfoque TDD (Test-Driven Development)**, garantizando calidad, mantenibilidad y confiabilidad en el código.

---

## 🎯 Objetivo Principal

- Implementar una aplicación modular de cálculo de nómina en Java.  
- Aplicar correctamente el **ciclo TDD** durante todo el proceso de desarrollo.  
- Desarrollar pruebas unitarias con **JUnit** antes de implementar la lógica funcional.  
- Promover buenas prácticas de diseño orientado a objetos y refactorización continua.  

---

## 🔁 Metodología: TDD (Test-Driven Development)

El desarrollo se guía por el ciclo iterativo de TDD:

1. **🟥 Red – Escribir una prueba que falle:**  
   Se redacta una prueba unitaria para una funcionalidad aún no implementada.  
   La prueba debe fallar inicialmente para confirmar que la lógica aún no existe.

2. **🟩 Green – Escribir el código mínimo necesario:**  
   Se implementa el código más simple que haga pasar la prueba.  
   No se busca optimización todavía.

3. **🟦 Refactor – Mejorar el código:**  
   Se refactoriza el código para mejorar su calidad sin cambiar su comportamiento.  
   Todas las pruebas deben seguir pasando tras la refactorización.

Este ciclo se repite con cada nueva funcionalidad del sistema.  

---

## 🧰 Tecnologías Utilizadas

- **Lenguaje:** Java (versión 17 o superior recomendada)  
- **Framework de Testing:** JUnit 5  
- **Control de versiones:** Git y GitHub  
- **IDE sugerido:** IntelliJ IDEA / Eclipse  

---

## Flujo TDD aplicado:

Se crea el test.

Se implementa hasta que la prueba pase.

Luego se refactoriza el código y se mantienen las pruebas verdes.

## 💡 Aprendizajes Esperados

Comprender y aplicar el ciclo completo de TDD en un proyecto Java real.

Diseñar clases orientadas a objetos limpias y fácilmente testeables.

Utilizar JUnit 5 para la automatización de pruebas.

Integrar la ejecución de pruebas con Maven o Gradle.

Promover la refactorización continua y el desarrollo incremental.

## 👨‍💻 Autores / Equipo

Veronica Lozada Perez y Bianca Luna

Proyecto desarrollado con fines educativos como práctica de Test i Qualitat de Software, asignatura de 3ero de Ingenieria Informatica, UAB.

# tqs_project

Este proyecto incluye modelos de `Worker` y `Company`, pruebas unitarias con repositorios mock y ahora una pequeña interfaz de línea de comandos para gestionar workers.

## Interfaz (CLI)

Se añadió `src/App.java` con un menú simple:
- Agregar worker
- Listar workers
- Eliminar worker por DNI
- Salir

### Ejecutar desde IntelliJ IDEA
1. Abrir el proyecto.
2. Asegúrate de que el SDK de Java esté configurado (Project Structure > SDK).
3. Crea una configuración de ejecución con la clase principal `App`.
4. Ejecuta y usa el menú interactivo.

### Ejecutar desde terminal (Linux)
Si tienes `javac` y `java` instalados:

```bash
# Compilar todas las clases bajo src
javac $(find src -name "*.java")

# Ejecutar el programa
java -cp src App
```

Si ves "Command 'javac' not found", instala un JDK:

```bash
sudo apt update
sudo apt install default-jdk
```

Luego compila y ejecuta como arriba.

### Listar/Imprimir todos los workers
En el menú, elige la opción "Listar workers". Verás los workers precargados más los que hayas agregado impresos usando `Worker.toString()`.

## Pruebas
Los tests están en el directorio `tests/` y usan repositorios mock para `Company` y `Worker`.

- Para imprimir workers desde los tests, mira `tests/WorkerTest.java` en el método `testSaveAndPrintAllWorkers()`.

## Notas
- La validación de DNI y otras reglas del dominio está implementada dentro de `model/Worker`.
- La CLI usa un repositorio en memoria interno y no depende de los repositorios de prueba.
