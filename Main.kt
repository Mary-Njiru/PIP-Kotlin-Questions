fun main() {
    // Example usage
    val userItemData = mapOf(
        "user1" to setOf("itemA", "itemB", "itemC"),
        "user2" to setOf("itemB", "itemC", "itemD"),
        // Add more user-item interactions here...
    )

    val recommendationSystem = RecommendationSystem(userItemData)
    val userId = "user1"
    val numRecommendations = 3
    val recommendations = recommendationSystem.getRecommendationsForUser(userId, numRecommendations)

    println("Recommendations for user $userId:")
    recommendations.forEachIndexed { index, recommendedUser ->
        println("${index + 1}. $recommendedUser")
    }
}


class RecommendationSystem(private val userItemMatrix: Map<String, Set<String>>) {
    // User-item interaction matrix (user -> set of items)

    fun getRecommendationsForUser(userId: String, numRecommendations: Int): List<String> {
        val userItems = userItemMatrix[userId] ?: emptySet()

        val recommendations = mutableMapOf<String, Double>()
        for ((otherUser, otherItems) in userItemMatrix) {
            if (otherUser != userId) {
                val intersection = userItems.intersect(otherItems).size.toDouble()
                val union = userItems.union(otherItems).size.toDouble()
                val Similarity = intersection / union
                recommendations[otherUser] = Similarity
            }
        }

        return recommendations.entries
            .sortedByDescending { it.value }
            .take(numRecommendations)
            .map { it.key }
    }
}

