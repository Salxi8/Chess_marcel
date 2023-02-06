import kotlin.math.absoluteValue

class Alfil(blanca_o_negra: Boolean, posicion: Posicion, seleccionada: Boolean)
    : Ficha(blanca_o_negra, posicion, seleccionada) {

    fun movimientoAlfil(tablero: Tablero, reiEncontrado: Rei) {
        var posFinalY = -1
        var posFinalX = -1
        // COMPROBEM SI LA FILA Y COLUMNA ON VOLEM MOURE EL ALFIL ESTA DINS DE L ARRAY DEL TABLERO
        // COMPROBEM QUE EL MOVIMENT ES PUGUI REALITZAR A TRAVES DE LA FUNCIÓ: POSIBILIDADESMOVIMIENTOALFIL()
        while ((posFinalX < 0 || posFinalX > 7) || (posFinalY < 0 || posFinalY > 7) || !(posibilidadesMovimientoAlfil(posFinalY, posFinalX, tablero))) { // PARA EVITAR QUE DE ERROR SI EL USUARIO ESCRIBE UNA CASILLA INVÁLIDA (POR EJEMPLO: P, o -1)

            println("------------------------------------------------------------------")
            print("|SI DESEA ELEJIR OTRA PIEZA PULSE [b] SINO PULSE CUALQUIER TECLA: ")
            val tirarAtras = readln()
            println("------------------------------------------------------------------")
            if (tirarAtras == "b"){
                return
            }

            print("En que FILA quiere mover la REINA [NUMERO]: ")
            val posFinalYAux = readln().toIntOrNull() ?: -1
            print("En que COLUMNA quiere mover la REINA [LETRA]: ")
            val posFinalXAux = readln().singleOrNull() ?: '-'.uppercaseChar()

            posFinalY = convertorLetraNumeroANumero(posFinalYAux, posFinalXAux).first
            posFinalX = convertorLetraNumeroANumero(posFinalYAux, posFinalXAux).second

            if ((posFinalX < 0 || posFinalX > 7) || (posFinalY < 0 || posFinalY > 7)) { // WARNING DE QUE LA FICHA NO SE PUEDE MOVER AHI
                println("La casilla [${posFinalY}, ${posFinalX}] no es una casilla válida dentro del tablero de ajedrez")
            }
            // SI LAS CORDENADAS DEL TABLERO NO SON VÁLIDAS COMPROBAR CON LA FUNCIÓN PRINTAR QUE ESA PIEZA NO PUEDE AVANZAR AHÍ
        }

        val elementoCasillaQueInvadimos = tablero[posFinalY, posFinalX]

        val auxAlfilJaque = Alfil(blanca_o_negra, Posicion(posFinalY, posFinalX), false)
        tablero.tablero[posFinalY][posFinalX] = auxAlfilJaque
        tablero.tablero[posicionFicha.fila][posicionFicha.columna] = null

        // PER EL JAQUE
        if (tablero.isKingInCheck(reiEncontrado)){

            tablero.tablero[posFinalY][posFinalX] = elementoCasillaQueInvadimos
            return

        }
        posicionFicha.fila = posFinalY
        posicionFicha.columna = posFinalX

        return
    }


    fun posibilidadesMovimientoAlfil(posFinalY: Int, posFinalX: Int, tablero: Tablero): Boolean {

        if (blanca_o_negra) { // ALFIL BLANCO
            if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY < posicionFicha.fila && posFinalX < posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posFinalX
                for (i in posFinalY..posicionFicha.fila) {
                        if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                            return false
                            // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA EL ALFIL HASTA LA POSICIÓN FINAL
                            // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                        }
                    auxPosicionFichaColumnas++
                }

                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR EL ALFIL EN DIAGONAL CAP A L'ESQUERRA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA

            }
            else if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY < posicionFicha.fila && posFinalX > posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posFinalX

                for (i in posFinalY..posicionFicha.fila) { // BIEN
                        if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                            return false
                            // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA EL ALFIL HASTA LA POSICIÓN FINAL
                            // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                        }
                    auxPosicionFichaColumnas--
                }

                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR EL ALFIL EN DIAGONAL CAP A LA DRETA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA

            }
            else if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY > posicionFicha.fila && posFinalX > posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posicionFicha.columna
                for (i in posicionFicha.fila .. posFinalY) {
                        if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                            return false
                            // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA EL ALFIL HASTA LA POSICIÓN FINAL
                            // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                        }
                    auxPosicionFichaColumnas++
                }

                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR EL ALFIL EN DIAGONAL CAP A LA DRETA I ABAIX
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA
            }

            else if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY > posicionFicha.fila && posFinalX < posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blanca_o_negra == false || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posicionFicha.columna
                for (i in posicionFicha.fila .. posFinalY) {
                    if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                        return false
                        // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA EL ALFIL HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                    auxPosicionFichaColumnas--
                }

                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR EL ALFIL EN DIAGONAL CAP A LA DRETA I ABAIX
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA NEGRA

            }
        } // ALFIL NEGRO
        else{
            if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY < posicionFicha.fila && posFinalX < posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posFinalX
                for (i in posFinalY..posicionFicha.fila) {
                    if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                        return false
                        // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA EL ALFIL HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                    auxPosicionFichaColumnas++
                }

                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR EL ALFIL EN DIAGONAL CAP A L'ESQUERRA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA

            }
            else if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY < posicionFicha.fila && posFinalX > posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posFinalX

                for (i in posFinalY..posicionFicha.fila) { // BIEN
                    if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                        return false
                        // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA EL ALFIL HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                    auxPosicionFichaColumnas--
                }

                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR EL ALFIL EN DIAGONAL CAP A LA DRETA I AMUNT
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA < FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA

            }
            else if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY > posicionFicha.fila && posFinalX > posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posicionFicha.columna
                for (i in posicionFicha.fila .. posFinalY) {
                    if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                        return false
                        // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA EL ALFIL HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                    auxPosicionFichaColumnas++
                }

                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR EL ALFIL EN DIAGONAL CAP A LA DRETA I ABAIX
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA > COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA
            }

            else if ((posFinalY - posicionFicha.fila).absoluteValue == (posFinalX - posicionFicha.columna).absoluteValue && posFinalY > posicionFicha.fila && posFinalX < posicionFicha.columna && (tablero[posFinalY, posFinalX]?.blanca_o_negra == true || tablero[posFinalY, posFinalX] == null)) {
                var auxPosicionFichaColumnas = posicionFicha.columna
                for (i in posicionFicha.fila .. posFinalY) {
                    if (tablero[i, auxPosicionFichaColumnas] != null && tablero[i, auxPosicionFichaColumnas] != tablero[posFinalY, posFinalX] && tablero[i, auxPosicionFichaColumnas] != tablero[posicionFicha.fila, posicionFicha.columna]) {
                        return false
                        // AQUÍ COMPROBAMOS QUE EN LA TRAYECTORIA QUE REALIZA EL ALFIL HASTA LA POSICIÓN FINAL
                        // NO HAYA NINGUNA FICHA ENTRE MEDIO YA QUE ENTONCES EL MOVIMIENTO NO SE PODRÍA REALIZAR
                    }
                    auxPosicionFichaColumnas--
                }

                return true
                // AQUEST MOVIMENT ENS PERMET AVANÇAR EL ALFIL EN DIAGONAL CAP A LA DRETA I ABAIX
                // ENS DONARÁ TRUE SI LA POSICIÓ QUE DONEM COMPLEIX:
                // 1. QUE EL VALOR ABSOLUT DE (POSFILASFINAL - POSFILASINICIAL) = VALOR ABSOLUT DE (POSCOLUMNESFINAL - POSCOLUMNESINICIAL)
                // 2. FILA FINAL DE LA FICHA > FILA INICIAL DE LA FICHA
                // 3. COLUMNA FINAL DE LA FICHA < COLUMNA INICIAL DE LA FICHA
                // 3. QUE LA POSICIÓ FINAL DE LA FICHA SIGUI NULLA O HI HAGI UNA FICHA BLANCA

            }
        }
        return false
    }
}