package cl.grupo11.mmconecta.repository

import cl.grupo11.mmconecta.model.LearningContent

interface ContentRepository {
    fun getContents(): List<LearningContent>
}

class DemoContentRepository : ContentRepository {
    override fun getContents() = listOf(
        LearningContent("1", "Primeros pasos en chocolatería", "Chocolatería",
            "Conoce técnicas básicas para comenzar a trabajar con chocolate.",
            listOf("Identifica el tipo de chocolate y revisa las instrucciones del fabricante.", "Prepara utensilios limpios y secos antes de comenzar.", "Registra el resultado de una prueba pequeña para comparar textura y acabado.")),
        LearningContent("2", "Ideas para tu vitrina", "Pastelería",
            "Explora ideas de presentación para tu emprendimiento.",
            listOf("Selecciona una familia de productos para destacar.", "Distribuye las piezas dejando espacio para reconocer cada producto.", "Prepara etiquetas legibles con nombre y precio para una vitrina de demostración.")),
        LearningContent("3", "Introducción a las masas", "Panadería",
            "Descubre conceptos básicos para preparar tus primeras masas.",
            listOf("Lee la receta completa y organiza los ingredientes.", "Pesa cada ingrediente y registra las cantidades utilizadas.", "Sigue los tiempos de la receta y anota los cambios para una próxima prueba."))
    )
}
