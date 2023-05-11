import time as t

from reversi import *
from algorithms import minimax, alpha_beta
from heuristics import difference_of_pieces, static_weights, pieces_and_weights, continuous_lines

# Human vs AI
def play(algorithm, depth, heuristic):
    game = Reversi()
    counter = 0
    while True:
        valid_moves = game.get_valid_moves()
        if not valid_moves:
            counter += 1
            if counter == 2:
                break
            else:
                print("No valid moves")
                game.current_player = 3 - game.current_player
                continue
        counter = 0
        print(f"Player {game.current_player}'s turn")
        game.print_board()
        print(f"Valid moves: {valid_moves}")

        if game.current_player == 2:
            start_time = time.time()
            best_eval, best_move, visited_nodes = algorithm(game, depth, heuristic, False)
            end_time = time.time()
            game.make_move(best_move[0], best_move[1])
            print("Visited nodes: " + str(visited_nodes))
            print("Best evaluation:  " + str(best_eval))
            print("Time: ", end_time - start_time)
            print()

        else:
            is_valid = False
            while not is_valid:
                row, col = map(int, input("Enter row and column: ").split())
                if (row, col) in valid_moves:
                    game.make_move(row, col)
                    is_valid = True
                else:
                    print("Invalid move")

    game.print_board()
    winner = game.get_winner()
    if winner == 0:
        print("It's a tie!")
    else:
        print(f"Player {winner} wins!")

#AI vs AI
def play_ai(algorithm, depth, heuristic1, heuristic2):
    game = Reversi()
    counter = 0
    while True:
        valid_moves = game.get_valid_moves()
        if not valid_moves:
            counter += 1
            if counter == 2:
                break
            else:
                print("No valid moves")
                game.current_player = 3 - game.current_player
                continue
        counter = 0
        print(f"Player {game.current_player}'s turn")
        game.print_board()
        print(f"Valid moves: {valid_moves}")

        if game.current_player == 1:
            start_time = t.time()
            best_eval, best_move, visited_nodes = algorithm(game, depth, heuristic1, True)
            end_time = t.time()
            exec_time = end_time - start_time
            game.make_move(best_move[0], best_move[1])
            print("Visited nodes: " + str(visited_nodes))
            print("Best evaluation:  " + str(best_eval))
            print("Time: ", exec_time)
            print()

        elif game.current_player == 2:
            start_time = t.time()
            best_eval, best_move, visited_nodes = algorithm(game, depth, heuristic2, False)
            end_time = t.time()
            exec_time = end_time - start_time
            game.make_move(best_move[0], best_move[1])
            print("Visited nodes: " + str(visited_nodes))
            print("Best evaluation:  " + str(best_eval))
            print("Time: ", exec_time)
            print()

    game.print_board()
    winner = game.get_winner()
    if winner == 0:
        print("It's a tie!")
    else:
        print(f"Player {winner} wins!")

if __name__ == "__main__":
    play_ai(alpha_beta, 3, difference_of_pieces, static_weights)
