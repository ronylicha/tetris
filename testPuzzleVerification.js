#!/usr/bin/env node

// Quick test script to verify and fix puzzles
import { puzzleVerifier } from './scripts/puzzles/puzzleVerificationSuite.js';
import { PUZZLES } from './scripts/puzzles/puzzleData.js';

async function testVerification() {
    console.log('🔍 Running puzzle verification test...\n');
    
    // Test specific problematic puzzles
    const testPuzzles = [2, 5, 10, 15, 20, 30, 50, 75, 100, 125, 150];
    
    for (const id of testPuzzles) {
        const puzzle = PUZZLES.find(p => p.id === id);
        if (!puzzle) {
            console.log(`❌ Puzzle ${id} not found`);
            continue;
        }
        
        console.log(`Testing Puzzle #${id}: ${puzzle.name}`);
        const result = await puzzleVerifier.verifyPuzzle(puzzle);
        
        if (result.solvable) {
            console.log(`✅ PASS - Difficulty: ${result.difficulty?.difficultyLabel || 'Unknown'}`);
        } else {
            console.log(`❌ FAIL - Issues: ${result.issues.join(', ')}`);
            
            // Try to generate fix
            const fix = puzzleVerifier.generateFix(puzzle, result);
            if (fix) {
                console.log(`  💡 Suggested fixes: ${fix.changes.join(', ')}`);
            }
        }
        console.log('');
    }
    
    // Run full verification
    console.log('Running full verification on all 150 puzzles...');
    const report = await puzzleVerifier.verifyAllPuzzles();
    
    console.log('\n📊 VERIFICATION SUMMARY');
    console.log('========================');
    console.log(`Total Puzzles: ${report.summary.total}`);
    console.log(`Solvable: ${report.summary.solvable}`);
    console.log(`Impossible: ${report.summary.impossible}`);
    console.log(`Success Rate: ${report.summary.successRate}`);
    
    if (report.impossiblePuzzles.length > 0) {
        console.log(`\n❌ Impossible puzzles: ${report.impossiblePuzzles.join(', ')}`);
        console.log(`\n💡 ${Object.keys(report.fixes).length} fixes available`);
        
        // Apply fixes
        console.log('\nApplying fixes...');
        const fixed = await puzzleVerifier.applyFixes();
        console.log(`Applied ${fixed.length} fixes`);
    }
    
    console.log('\n✅ Verification complete!');
}

testVerification().catch(console.error);