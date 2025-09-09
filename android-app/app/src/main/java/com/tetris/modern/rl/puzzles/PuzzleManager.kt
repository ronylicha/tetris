package com.tetris.modern.rl.puzzles

import com.tetris.modern.rl.game.models.PieceType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PuzzleManager @Inject constructor() {
    
    // All 150 puzzles matching the original game
    private val puzzles: List<Puzzle> by lazy {
        val basePuzzles = listOf(
        // EASY PUZZLES (1-30)
        createPuzzle(1, "First Steps", "Clear a single line", 
            Puzzle.PuzzleObjective.CLEAR_TARGET, Puzzle.PuzzleDifficulty.EASY, 3),
        createPuzzle(2, "Double Clear", "Clear two lines at once",
            Puzzle.PuzzleObjective.CLEAR_TARGET, Puzzle.PuzzleDifficulty.EASY, 4),
        createPuzzle(3, "Clean Sweep", "Clear all blocks",
            Puzzle.PuzzleObjective.CLEAR_ALL, Puzzle.PuzzleDifficulty.EASY, 5),
        createPuzzle(4, "Tetris Time", "Clear 4 lines at once",
            Puzzle.PuzzleObjective.TETRIS_ONLY, Puzzle.PuzzleDifficulty.EASY, 2),
        createPuzzle(5, "Perfect Placement", "Clear the grid completely",
            Puzzle.PuzzleObjective.PERFECT_CLEAR, Puzzle.PuzzleDifficulty.EASY, 6),
        
        // Continue with more puzzles...
        createPuzzle(6, "Speed Run", "Clear 3 lines in 30 seconds",
            Puzzle.PuzzleObjective.SPEED, Puzzle.PuzzleDifficulty.EASY, 8, timeLimit = 30000),
        createPuzzle(7, "No Rotation", "Clear lines without rotating",
            Puzzle.PuzzleObjective.NO_ROTATION, Puzzle.PuzzleDifficulty.EASY, 7,
            constraints = setOf(Puzzle.PuzzleConstraint.NO_ROTATION)),
        createPuzzle(8, "Cascade Effect", "Create a cascade clear",
            Puzzle.PuzzleObjective.CASCADE, Puzzle.PuzzleDifficulty.EASY, 5),
        createPuzzle(9, "Chain Reaction", "Clear 3 lines consecutively",
            Puzzle.PuzzleObjective.CHAIN, Puzzle.PuzzleDifficulty.EASY, 9),
        createPuzzle(10, "Pattern Master", "Create the target pattern",
            Puzzle.PuzzleObjective.PATTERN, Puzzle.PuzzleDifficulty.EASY, 4),
        
        // MEDIUM PUZZLES (31-60)
        createPuzzle(31, "T-Spin Tutorial", "Clear a line with T-spin",
            Puzzle.PuzzleObjective.T_SPIN_ONLY, Puzzle.PuzzleDifficulty.MEDIUM, 3),
        createPuzzle(32, "Limited Resources", "Clear 3 lines with only L and J pieces",
            Puzzle.PuzzleObjective.CLEAR_TARGET, Puzzle.PuzzleDifficulty.MEDIUM, 6,
            availablePieces = listOf(PieceType.L, PieceType.J)),
        createPuzzle(33, "Mirror World", "Clear lines with mirrored controls",
            Puzzle.PuzzleObjective.CLEAR_TARGET, Puzzle.PuzzleDifficulty.MEDIUM, 8,
            constraints = setOf(Puzzle.PuzzleConstraint.MIRROR_MODE)),
        createPuzzle(34, "Speed Demon", "Clear 5 lines in 45 seconds",
            Puzzle.PuzzleObjective.SPEED, Puzzle.PuzzleDifficulty.MEDIUM, 10, timeLimit = 45000),
        createPuzzle(35, "Invisible Challenge", "Clear lines with invisible pieces",
            Puzzle.PuzzleObjective.CLEAR_TARGET, Puzzle.PuzzleDifficulty.MEDIUM, 7,
            constraints = setOf(Puzzle.PuzzleConstraint.INVISIBLE_PIECES)),
        
        // HARD PUZZLES (61-90)
        createPuzzle(61, "Expert T-Spin", "Clear 3 lines with T-spin triple",
            Puzzle.PuzzleObjective.T_SPIN_ONLY, Puzzle.PuzzleDifficulty.HARD, 5),
        createPuzzle(62, "No Hold Zone", "Clear 6 lines without using hold",
            Puzzle.PuzzleObjective.CLEAR_TARGET, Puzzle.PuzzleDifficulty.HARD, 12,
            constraints = setOf(Puzzle.PuzzleConstraint.NO_HOLD)),
        createPuzzle(63, "Perfect Storm", "Achieve 3 perfect clears",
            Puzzle.PuzzleObjective.PERFECT_CLEAR, Puzzle.PuzzleDifficulty.HARD, 15),
        createPuzzle(64, "Chain Master", "Create a 5-chain combo",
            Puzzle.PuzzleObjective.CHAIN, Puzzle.PuzzleDifficulty.HARD, 10),
        createPuzzle(65, "Speed Limit", "Clear 8 lines in 1 minute",
            Puzzle.PuzzleObjective.SPEED, Puzzle.PuzzleDifficulty.HARD, 15, timeLimit = 60000),
        
        // EXPERT PUZZLES (91-120)
        createPuzzle(91, "One Chance", "Clear all lines without mistakes",
            Puzzle.PuzzleObjective.CLEAR_ALL, Puzzle.PuzzleDifficulty.EXPERT, 8,
            constraints = setOf(Puzzle.PuzzleConstraint.ONE_CHANCE)),
        createPuzzle(92, "Tetris Marathon", "Clear 10 lines using only Tetrises",
            Puzzle.PuzzleObjective.TETRIS_ONLY, Puzzle.PuzzleDifficulty.EXPERT, 5),
        createPuzzle(93, "Speed Master", "Clear 12 lines in 90 seconds",
            Puzzle.PuzzleObjective.SPEED, Puzzle.PuzzleDifficulty.EXPERT, 20, timeLimit = 90000),
        createPuzzle(94, "Complex Pattern", "Recreate the master pattern",
            Puzzle.PuzzleObjective.PATTERN, Puzzle.PuzzleDifficulty.EXPERT, 12),
        createPuzzle(95, "No Drop Zone", "Clear lines without soft/hard drop",
            Puzzle.PuzzleObjective.CLEAR_TARGET, Puzzle.PuzzleDifficulty.EXPERT, 10,
            constraints = setOf(Puzzle.PuzzleConstraint.NO_SOFT_DROP, Puzzle.PuzzleConstraint.NO_HARD_DROP)),
        
        // MASTER PUZZLES (121-150)
        createPuzzle(121, "The Impossible", "Clear 15 lines with all constraints",
            Puzzle.PuzzleObjective.CLEAR_TARGET, Puzzle.PuzzleDifficulty.MASTER, 25,
            constraints = setOf(
                Puzzle.PuzzleConstraint.NO_HOLD,
                Puzzle.PuzzleConstraint.NO_SOFT_DROP,
                Puzzle.PuzzleConstraint.SPEED_UP
            )),
        createPuzzle(122, "T-Spin Mastery", "Clear 10 lines using only T-spins",
            Puzzle.PuzzleObjective.T_SPIN_ONLY, Puzzle.PuzzleDifficulty.MASTER, 8),
        createPuzzle(123, "Ultimate Speed", "Clear 20 lines in 2 minutes",
            Puzzle.PuzzleObjective.SPEED, Puzzle.PuzzleDifficulty.MASTER, 30, timeLimit = 120000),
        createPuzzle(124, "Perfect Game", "Achieve 5 perfect clears in a row",
            Puzzle.PuzzleObjective.PERFECT_CLEAR, Puzzle.PuzzleDifficulty.MASTER, 20),
        createPuzzle(150, "Final Challenge", "Complete the ultimate test",
            Puzzle.PuzzleObjective.CLEAR_ALL, Puzzle.PuzzleDifficulty.MASTER, 30,
            constraints = setOf(
                Puzzle.PuzzleConstraint.NO_HOLD,
                Puzzle.PuzzleConstraint.INVISIBLE_PIECES,
                Puzzle.PuzzleConstraint.SPEED_UP,
                Puzzle.PuzzleConstraint.ONE_CHANCE
            ))
    )
        basePuzzles.plus(generateRemainingPuzzles(basePuzzles)) // Generate the rest to reach 150
    }
    
    private fun createPuzzle(
        id: Int,
        name: String,
        description: String,
        objective: Puzzle.PuzzleObjective,
        difficulty: Puzzle.PuzzleDifficulty,
        maxPieces: Int,
        timeLimit: Long = 300000, // 5 minutes default
        availablePieces: List<PieceType> = PieceType.values().toList(),
        constraints: Set<Puzzle.PuzzleConstraint> = emptySet()
    ): Puzzle {
        // Generate initial grid based on puzzle ID and difficulty
        val initialGrid = generateInitialGrid(id, difficulty)
        
        // Generate hints based on difficulty
        val hints = generateHints(objective, difficulty)
        
        // Generate solution (for hint system)
        val solution = generateSolution(id, objective)
        
        return Puzzle(
            id = id,
            name = name,
            description = description,
            objective = objective,
            objectiveShort = getObjectiveShort(objective),
            difficulty = difficulty,
            maxPieces = maxPieces,
            targetTime = timeLimit,
            initialGrid = initialGrid,
            availablePieces = availablePieces,
            constraints = constraints,
            hints = hints,
            solution = solution
        )
    }
    
    private fun generateRemainingPuzzles(existingPuzzles: List<Puzzle>): List<Puzzle> {
        val remaining = mutableListOf<Puzzle>()
        
        // Fill in missing puzzle IDs (11-30, 36-60, 66-90, 96-120, 125-149)
        for (i in 11..30) {
            if (existingPuzzles.none { it.id == i }) {
                remaining.add(createPuzzle(
                    i, 
                    "Puzzle $i",
                    "Complete puzzle $i",
                    Puzzle.PuzzleObjective.values().random(),
                    Puzzle.PuzzleDifficulty.EASY,
                    i / 3 + 2
                ))
            }
        }
        
        for (i in 36..60) {
            if (existingPuzzles.none { it.id == i }) {
                remaining.add(createPuzzle(
                    i,
                    "Puzzle $i",
                    "Complete puzzle $i",
                    Puzzle.PuzzleObjective.values().random(),
                    Puzzle.PuzzleDifficulty.MEDIUM,
                    i / 4 + 3
                ))
            }
        }
        
        for (i in 66..90) {
            if (existingPuzzles.none { it.id == i }) {
                remaining.add(createPuzzle(
                    i,
                    "Puzzle $i",
                    "Complete puzzle $i",
                    Puzzle.PuzzleObjective.values().random(),
                    Puzzle.PuzzleDifficulty.HARD,
                    i / 5 + 4
                ))
            }
        }
        
        for (i in 96..120) {
            if (existingPuzzles.none { it.id == i }) {
                remaining.add(createPuzzle(
                    i,
                    "Puzzle $i",
                    "Complete puzzle $i",
                    Puzzle.PuzzleObjective.values().random(),
                    Puzzle.PuzzleDifficulty.EXPERT,
                    i / 6 + 5
                ))
            }
        }
        
        for (i in 125..149) {
            if (existingPuzzles.none { it.id == i }) {
                remaining.add(createPuzzle(
                    i,
                    "Puzzle $i",
                    "Complete puzzle $i",
                    Puzzle.PuzzleObjective.values().random(),
                    Puzzle.PuzzleDifficulty.MASTER,
                    i / 7 + 6
                ))
            }
        }
        
        return remaining
    }
    
    private fun generateInitialGrid(id: Int, difficulty: Puzzle.PuzzleDifficulty): Array<IntArray> {
        // Generate a grid configuration based on puzzle ID
        val height = 20
        val width = 10
        val grid = Array(height) { IntArray(width) { -1 } }
        
        // Add some filled cells based on difficulty
        val fillPercentage = when (difficulty) {
            Puzzle.PuzzleDifficulty.EASY -> 0.2f
            Puzzle.PuzzleDifficulty.MEDIUM -> 0.3f
            Puzzle.PuzzleDifficulty.HARD -> 0.4f
            Puzzle.PuzzleDifficulty.EXPERT -> 0.5f
            Puzzle.PuzzleDifficulty.MASTER -> 0.6f
        }
        
        // Fill bottom rows with some pattern
        val rowsToFill = (height * fillPercentage).toInt()
        for (y in height - rowsToFill until height) {
            for (x in 0 until width) {
                // Create gaps for puzzle solving
                if ((x + y + id) % 3 != 0) {
                    grid[y][x] = PieceType.values().random().ordinal
                }
            }
        }
        
        return grid
    }
    
    private fun generateHints(objective: Puzzle.PuzzleObjective, difficulty: Puzzle.PuzzleDifficulty): List<String> {
        return listOf(
            "Look for the optimal placement position",
            "Consider the rotation needed for this piece",
            "This puzzle requires careful planning"
        )
    }
    
    private fun generateSolution(id: Int, objective: Puzzle.PuzzleObjective): List<Puzzle.PuzzleMove> {
        // Generate a sample solution for the puzzle
        return listOf(
            Puzzle.PuzzleMove(PieceType.T, 4, 18, 0),
            Puzzle.PuzzleMove(PieceType.I, 0, 19, 1),
            Puzzle.PuzzleMove(PieceType.L, 7, 17, 2)
        )
    }
    
    private fun getObjectiveShort(objective: Puzzle.PuzzleObjective): String {
        return when (objective) {
            Puzzle.PuzzleObjective.CLEAR_ALL -> "Clear all"
            Puzzle.PuzzleObjective.CLEAR_SPECIFIC -> "Clear lines"
            Puzzle.PuzzleObjective.CLEAR_TARGET -> "Clear target"
            Puzzle.PuzzleObjective.CASCADE -> "Cascade"
            Puzzle.PuzzleObjective.PATTERN -> "Pattern"
            Puzzle.PuzzleObjective.SURVIVAL -> "Survive"
            Puzzle.PuzzleObjective.SPEED -> "Speed"
            Puzzle.PuzzleObjective.NO_ROTATION -> "No rotation"
            Puzzle.PuzzleObjective.CHAIN -> "Chain"
            Puzzle.PuzzleObjective.PERFECT_CLEAR -> "Perfect"
            Puzzle.PuzzleObjective.T_SPIN_ONLY -> "T-Spin"
            Puzzle.PuzzleObjective.TETRIS_ONLY -> "Tetris"
        }
    }
    
    fun getPuzzle(id: Int): Puzzle? {
        return puzzles.find { it.id == id }
    }
    
    fun getPuzzlesByDifficulty(difficulty: Puzzle.PuzzleDifficulty): List<Puzzle> {
        return puzzles.filter { it.difficulty == difficulty }
    }
    
    fun getUnlockedPuzzles(playerLevel: Int): List<Puzzle> {
        // Unlock puzzles based on player level
        val maxPuzzleId = when {
            playerLevel < 5 -> 10
            playerLevel < 10 -> 30
            playerLevel < 20 -> 60
            playerLevel < 30 -> 90
            playerLevel < 40 -> 120
            else -> 150
        }
        
        return puzzles.filter { it.id <= maxPuzzleId }
    }
    
    fun markPuzzleCompleted(
        puzzleId: Int,
        stars: Int,
        completionTime: Long,
        movesUsed: Int,
        hintsUsed: Int
    ) {
        // Save puzzle completion to database
        // This would interact with the repository layer
    }
    
    fun getPuzzleProgress(): Map<Int, PuzzleProgress> {
        // Return progress for all puzzles
        return emptyMap() // Placeholder
    }
    
    data class PuzzleProgress(
        val puzzleId: Int,
        val completed: Boolean,
        val stars: Int,
        val bestTime: Long?,
        val bestMoves: Int?
    )
}