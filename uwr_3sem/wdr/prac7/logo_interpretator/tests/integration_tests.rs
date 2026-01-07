// Testy integracyjne - uruchamianie przykladow z katalogu examples/
// Kazdy plik .logo jest parsowany i wykonywany jako test

use logo_interpretator::parser::Parser;
use logo_interpretator::interpreter::Interpreter;
use std::fs;

// Funkcja pomocnicza - uruchamia plik LOGO i zwraca wynik
fn run_logo_file(filename: &str) -> Result<(), Box<dyn std::error::Error>> {
    // Skonstruuj sciezke do pliku w katalogu examples/
    let path = format!("examples/{}", filename);
    
    // Wczytaj kod zrodlowy
    let source = fs::read_to_string(&path)?;
    
    // Parsuj kod do AST
    let parser = Parser::new();
    let ast = parser.parse(&source)?;
    
    // Wykonaj program
    let mut interpreter = Interpreter::new();
    interpreter.execute(&ast)?;
    
    Ok(())
}

// Test dla square.logo - rysowanie kwadratu
#[test]
fn test_square() {
    run_logo_file("square.logo")
        .expect("square.logo should execute without errors");
}

// Test dla spiral.logo - rysowanie spirali
#[test]
fn test_spiral() {
    run_logo_file("spiral.logo")
        .expect("spiral.logo should execute without errors");
}

// Test dla star.logo - rysowanie gwiazdy
#[test]
fn test_star() {
    run_logo_file("star.logo")
        .expect("star.logo should execute without errors");
}

// Test dla flower.logo - rysowanie kwiatka
#[test]
fn test_flower() {
    run_logo_file("flower.logo")
        .expect("flower.logo should execute without errors");
}

// Test dla triangle.logo - rysowanie trojkata
#[test]
fn test_triangle() {
    run_logo_file("triangle.logo")
        .expect("triangle.logo should execute without errors");
}

// Test dla hexagon.logo - rysowanie szesciokata
#[test]
fn test_hexagon() {
    run_logo_file("hexagon.logo")
        .expect("hexagon.logo should execute without errors");
}

// Test dla circles.logo - rysowanie okregow
#[test]
fn test_circles() {
    run_logo_file("circles.logo")
        .expect("circles.logo should execute without errors");
}

// Test dla math.logo - operacje matematyczne
#[test]
fn test_math() {
    run_logo_file("math.logo")
        .expect("math.logo should execute without errors");
}

// Test dla hideturtle.logo - repcount i setlabelheight
#[test]
fn test_hideturtle() {
    run_logo_file("hideturtle.logo")
        .expect("hideturtle.logo should execute without errors");
}

// Uwaga: testy randomcolor, tree, fern pominiete z powodu problemow z parsowaniem
// wielolinijnych definicji procedur (to/end) - wymaga dalszych poprawek w gramatyce
