# Sistema de Gestión de Empresas y Empleados

## 📋 Descripción del Proyecto

Sistema de gestión desarrollado para la asignatura **Test y Calidad (Curso 2025-2026)** que permite administrar organizaciones, gestionar trabajadores y calcular nóminas de manera eficiente.

El sistema implementa dos tipos de acceso diferenciados (empresa y empleado) con funciones, restricciones y mecanismos de control específicos para garantizar la integridad, coherencia y seguridad de la información.

---

## 🎯 Funcionalidades Principales

### 👔 Perfil Empresa
- **Registrar trabajadores**: Alta de nuevos empleados con validación de datos
- **Gestionar plantilla**: Visualización de todos los trabajadores de la empresa
- **Eliminar trabajadores**: Baja de empleados del sistema
- **Calcular nóminas**: Cálculo automático considerando IRPF, Seguridad Social y variables personalizables

### 👤 Perfil Empleado
- **Consultar datos personales**: Acceso a información laboral propia
- **Visualizar directorio**: Consulta en modo lectura del listado general de empleados

---

## 📊 Validaciones del Modelo

### Worker (Trabajador)
| Campo | Validaciones |
|-------|-------------|
| DNI | Formato 8 dígitos + letra, verificación de letra válida |
| Estado Civil | Lista cerrada: Soltero, Casado, Divorciado, Viudo |
| Hijos | Entero >= 0 |
| Salario | Float > 0 (salario mínimo implícito) |
| Pagas | 12 o 14 exactamente |
| Contrato | Indefinido, Temporal, Formación en Alternancia, Formativo |
| Categoría | Rango [0-10] |
| CIF Empresa | Formato básico de CIF validado |

### Company (Empresa)
| Campo | Validaciones |
|-------|-------------|
| CIF | No vacío, formato básico |
| Email | Expresión regular RFC 5322 simplificada |
| CNAE | Código de actividad económica válido (lista cerrada) |

### Payroll (Nómina)
- **IRPF**: Cálculo por tramos progresivos (19%-45%)
  - Reducción: -1% por hijo (máx. -5%)
  - Incremento: +3% si contrato temporal
- **Seguridad Social**: Por categoría profesional (6.35%-6.45%)
- **Salario Neto**: Bruto anual - (IRPF + SS)

---

## 📝 Uso del Sistema

### Flujo de Autenticación
1. Al iniciar, seleccionar tipo de usuario:
   - `1` → Empresa (ingresar CIF)
   - `2` → Trabajador (ingresar DNI)

### Menú Empresa
```
1. Agregar nuevo trabajador
   - Captura datos con validación en tiempo real
   - Asigna automáticamente el CIF de la empresa

2. Listar plantilla
   - Tabla formateada con todos los empleados
   - Filtrado por CIF de empresa

3. Eliminar trabajador
   - Solicita confirmación antes de borrar
   - Validación de existencia del DNI
```

### Menú Trabajador
```
1. Ver mis datos personales
   - Ficha detallada vertical
   - Información laboral completa

2. Ver listado general
   - Solo lectura del directorio completo
   - Sin permisos de modificación
```
---

## 👥 Equipo de Desarrollo

- **Integrante 1**: [Bianca Luna] - [bianca.luna@autonoma.cat]
- **Integrante 2**: [Verónica Lozada Pérez] - [veronica.lozada@autonoma.cat]

---

## 🔧 Tecnologías Utilizadas

- **Lenguaje**: Java 11+
- **Testing**: JUnit 5, Mockito
- **Control de Versiones**: Git + GitHub
- **CI/CD**: GitHub Actions
- **Calidad de Código**: Checkstyle

---

## 📖 Referencias

- [Documentación JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/)
- [GitHub Actions for Java](https://docs.github.com/en/actions/guides/building-and-testing-java-with-maven)
- Apuntes de la asignatura Test i Qualitat (UAB)

---

## 📄 Licencia

Este proyecto es parte de un trabajo académico para la Universitat Autònoma de Barcelona.  
© 2025 - Todos los derechos reservados.
