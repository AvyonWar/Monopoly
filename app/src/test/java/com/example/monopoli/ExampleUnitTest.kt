package com.example.monopoly

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class MonopolyUnitTest {

    //--- RELEASE1 -----------------------------------
    // si muove correttamente?
    @Test  // fallisce se la lista presenta meno di 2 o più di 8 giocatori
    fun testSpostamentoGiocatore() {
        val giocatore1 = Giocatori("player1")
        val giocatore2 = Giocatori("player2")
        val giocatori = listOf(giocatore1, giocatore2)
        val game = Monopoly(giocatori)

        game.playMonopoly()

        //test movimento con un tiro di dado = a 8 da casella 0 -> posizione 8
        giocatore1.posizione = 0
        val tiroDado = 8
        giocatore1.posizione = (giocatore1.posizione + tiroDado) % 40
        assertEquals(8, giocatore1.posizione)

        //test movimento giocatore con tiro = 10 e con partenza da casella 35 -> posizione 5
        giocatore1.posizione = 35
        val tiroDado2 = 10
        giocatore1.posizione = (giocatore1.posizione + tiroDado2) % 40
        assertEquals(5, giocatore1.posizione)
    }

    //sono 20 i turni?
    @Test
    fun testEsecuzioneRound() {
        val giocatori = listOf(Giocatori("player1"), Giocatori("player2"))
        val game = Monopoly(giocatori)
        game.playMonopoly()

        // sono solo 20?
        assertEquals(20, game.roundGiocatore)

    }

    //l'ordine NON varia?
    @Test
    fun testOrdineGiocatori() {
        //creo una lista manuale dei gioicatori
        val giocatori = listOf(Giocatori("player1"), Giocatori("player2"))
        val game = Monopoly(giocatori)

        //salvo la posizione iniziale
        val posizioneIniziale = giocatori.map { it.nome }
        game.playMonopoly()
        //salvo la posizione finale
        val posizioneFinale = giocatori.map { it.nome }

        //verifico che l'ordine di gioco non è cambiato
        assertEquals(posizioneIniziale, posizioneFinale)
    }

    // l'ordine varia al riavvio?
    @Test
    fun testCambioOrdine() {
        val giocatori = listOf(Giocatori("player1"), Giocatori("player2"))
        val game = Monopoly(giocatori)


        val posizioneIniziale = giocatori.map { it.nome }
        game.playMonopoly()
        val posizioneFinale = giocatori.map { it.nome }

        assertNotEquals(posizioneIniziale, posizioneFinale)
    }

    //--- RELEASE2 -----------------------------------

    //passando dal via credito + 200
    @Test
    fun testCredito(){
        val giocatore1 = Giocatori("player1")
        val giocatore2 = Giocatori("player2")
        val giocatori = listOf(giocatore1, giocatore2)
        val game = Monopoly(giocatori)

        game.playMonopoly()
        giocatore1.posizione = 0
        giocatore1.credito = 0
        game.round()
        assertEquals(200, giocatore1.credito)

    }

    // su casella X il saldo non varia
    @Test
    fun testNonVariazioneCredito(){
        val giocatore1 = Giocatori("player1")
        val giocatore2 = Giocatori("player2")
        val giocatori = listOf(giocatore1, giocatore2)
        val game = Monopoly(giocatori)

        game.playMonopoly()

        giocatore1.posizione = 20
        val credito = giocatore1.credito
        game.round()

        assertEquals(credito, giocatore1.credito)
    }

    // andare in prigione da 30 a 10 senza aspettare il turno
    @Test
    fun testVaiInPrigione(){
        val giocatore1 = Giocatori("Horse")
        val game = Monopoly(listOf(giocatore1))


        giocatore1.posizione = 25
        val tiroDado = 5
        giocatore1.posizione = (giocatore1.posizione + tiroDado)
        game.round()
        assertEquals(10, giocatore1.posizione)
    }

    // sassa patrimoniale?
    @Test
    fun testTassaPatrimoniale() {

        /**
         * xk non funziona con :  (?)
        val giocatore1 = Giocatori("player1")
        val giocatore2 = Giocatori("player2")
        val giocatori = listOf(giocatore1, giocatore2)
        val game = Monopoly(giocatori)
         */

        val giocatore1 = Giocatori("Horse")
        val game = Monopoly(listOf(giocatore1))
//20%
        giocatore1.posizione = 5
        giocatore1.credito = 900
        game.round()

        assertEquals(720, giocatore1.credito)

        giocatore1.posizione = 5
        giocatore1.credito = 2200
        game.round()

        assertEquals(2000, giocatore1.credito)

        giocatore1.posizione = 5
        giocatore1.credito = 0
        game.round()

        assertEquals(0, giocatore1.credito)

        giocatore1.posizione = 5
        giocatore1.credito = 2000
        game.round()

        assertEquals(1800, giocatore1.credito)
    }

    // tassa di lusso?
    @Test
    fun testTassaDiLusso() {

        /**
         * xk non funziona con :  (?)
        val giocatore1 = Giocatori("player1")
        val giocatore2 = Giocatori("player2")
        val giocatori = listOf(giocatore1, giocatore2)
        val game = Monopoly(giocatori)
         */

        val giocatore1 = Giocatori("Car")
        val game = Monopoly(listOf(giocatore1))

        giocatore1.posizione = 45
        giocatore1.credito = 500
        game.round()

        assertEquals(425, giocatore1.credito)

        giocatore1.posizione = 45
        giocatore1.credito = 1000
        game.round()

        assertEquals(925, giocatore1.credito)
    }
}