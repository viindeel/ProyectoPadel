# 🎾 Padel Pro Tracker - Sprint I

Aplicación multiplataforma (Android y React) orientada a aficionados al pádel profesional. Permite seguir torneos, partidos y jugadores del circuito profesional, consultando resultados, calendarios y estadísticas a través de una API REST centralizada.

---

## 📝 1. Análisis de Entorno y Requisitos

### Contexto
El pádel cuenta con una comunidad creciente que consume datos mayoritariamente desde dispositivos móviles.

### Problema
La información actual está dispersa en múltiples fuentes y plataformas no optimizadas para el consumo rápido.

### Solución
Centralizar la información en una plataforma única con arquitectura cliente-servidor para garantizar coherencia y escalabilidad.

---

## 👤 Persona

**Nombre:** David López (26 años)

**Perfil:** Estudiante y aficionado que consulta resultados y cuadros desde el móvil en fines de semana de torneo.

**Objetivos:** Seguir torneos en curso y conocer próximos enfrentamientos.

**Frustraciones:** Interfaces poco claras y dificultad para hallar calendarios actualizados.

---

## 🎨 2. Diseño y Prototipado (Material Design 3)

La aplicación prioriza la legibilidad y la claridad de los datos deportivos.

### Paleta de Colores

- **Verde oscuro (Principal):** Deporte y competición
- **Negro / Gris oscuro (Secundario):** Contraste y sobriedad
- **Blanco / Grises claros (Apoyo):** Legibilidad y reducción de fatiga visual

### Tipografía
Fuente karla para alta legibilidad en móviles y estilo moderno.

### Paradigma Material

- **Android:** Uso de Material 3 con tema personalizado, soporte para Dynamic Color y componentes como Cards y TopAppBar
- **Web (React):** Diseño inspirado en Material 3 con componentes reutilizables

### Prototipo

---

## 🚀 3. Historias de Usuario y API REST

Cada historia de usuario está vinculada directamente a una llamada de la API centralizada.

| ID | Historia de Usuario | Objetivo | Endpoint API |
|----|---------------------|----------|--------------|
| HU1 | Visualización de torneos | Consultar competiciones programadas | `GET /tournaments` |
| HU2 | Detalle de un torneo | Consultar calendario y rondas | `GET /tournaments/{id}` |
| HU3 | Partidos de un torneo | Seguir resultados de un torneo concreto | `GET /tournaments/{id}/matches` |
| HU4 | Detalle de un partido | Conocer resultado y estado del encuentro | `GET /matches/{id}` |
| HU5 | Información de jugador | Conocer trayectoria y participación | `GET /players/{id}` |
| HU6 | Partidos de un jugador | Seguir rendimiento del jugador en el circuito | `GET /players/{id}/matches` |

---

## 📱 4. Flujo de la Aplicación y Componentes

La navegación es jerárquica: **Lista → Detalle → Subdetalle**

### Componentes Principales

- **Lista de Torneos:** Pantalla inicial con RecyclerView dentro de un Fragment
- **Detalle de Torneo:** Vista general con acceso a partidos asociados
- **Listados de Partidos/Jugadores:** Implementados mediante RecyclerView y Cards para representar entidades deportivas
- **Navegación:** Uso de Fragments para transiciones fluidas entre vistas

---

## 🏗️ 5. Arquitectura Técnica

Sistema basado en una arquitectura **Cliente–Servidor**.

### ☁️ Backend e Información

- **Origen de datos:** Consume la API pública de `padelapi.org` y realiza web scraping controlado de `padelfip.com`
- **Función:** Normaliza y expone los datos mediante una API REST propia

### 🤖 Android (MVVM)

- **View:** Activities, Fragments y RecyclerView
- **ViewModel:** Gestiona el estado de la UI y solicita datos al repositorio
- **Repository:** Encapsula las llamadas a la API REST

### ⚛️ React

- **Componentes:** Vistas principales y detalles reutilizables
- **Servicios:** Encargados de la comunicación con la API
- **Hooks:** Gestión del estado y ciclo de vida de los datos

---

## 📊 6. Modelo Entidad–Relación

El modelo representa la estructura del circuito profesional y permite futuras ampliaciones.

### Relaciones

- **Torneo (1:N) Partido:** Un torneo contiene múltiples partidos
- **Partido (N:M) Jugador:** Un partido involucra a varios jugadores y un jugador participa en muchos partidos

### Entidades principales
- Usuario
- Torneo
- Partido
- Jugador

---
