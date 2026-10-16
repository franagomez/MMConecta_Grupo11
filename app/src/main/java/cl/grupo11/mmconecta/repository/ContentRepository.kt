package cl.grupo11.mmconecta.repository

import cl.grupo11.mmconecta.model.LearningContent

interface ContentRepository {
    fun getContents(): List<LearningContent>
}

class DemoContentRepository : ContentRepository {
    override fun getContents() = listOf(
        LearningContent("1", "Primeros pasos en chocolatería", "Chocolatería",
            "Conoce técnicas básicas para comenzar a trabajar con chocolate."),
        LearningContent("2", "Ideas para tu vitrina", "Pastelería",
            "Explora ideas de presentación para tu emprendimiento."),
        LearningContent("3", "Introducción a las masas", "Panadería",
            "Descubre conceptos básicos para preparar tus primeras masas.")
    )
}
