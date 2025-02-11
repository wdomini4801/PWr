import copy
import time

# Implementation of minimax algorithm
def minimax(game, depth, heuristic, maximizing_player, visited_nodes=0):
    visited_nodes += 1

    possible_moves = game.get_valid_moves()

    if depth == 0 or len(possible_moves) == 0:
        return heuristic(game), None, visited_nodes

    if maximizing_player:
        max_eval = float('-inf')
        best_move = None
        for move in possible_moves:
            game_copy = copy.deepcopy(game)
            game_copy.make_move(move[0], move[1])
            eval, some_move, nodes = minimax(game_copy, depth - 1, heuristic, False, 0)
            if eval > max_eval:
                max_eval = eval
                best_move = move
            visited_nodes += nodes
        return max_eval, best_move, visited_nodes

    else:
        min_eval = float('inf')
        best_move = None
        for move in possible_moves:
            game_copy = copy.deepcopy(game)
            game_copy.make_move(move[0], move[1])
            eval, some_move, nodes = minimax(game_copy, depth - 1, heuristic, True, 0)
            if eval < min_eval:
                min_eval = eval
                best_move = move
            visited_nodes += nodes
        return min_eval, best_move, visited_nodes

# Implementation of alpha-beta pruning
def alpha_beta(game, depth, heuristic, maximizing_player, alpha=float('-inf'), beta=float('inf'), visited_nodes=0):
    visited_nodes += 1

    possible_moves = game.get_valid_moves()

    if depth == 0 or len(possible_moves) == 0:
        return heuristic(game), None, visited_nodes

    if maximizing_player:
        max_eval = float('-inf')
        best_move = None
        for move in possible_moves:
            game_copy = copy.deepcopy(game)
            game_copy.make_move(move[0], move[1])
            eval, some_move, nodes = alpha_beta(game_copy, depth - 1, heuristic, False, alpha, beta, 0)
            if eval > max_eval:
                max_eval = eval
                best_move = move
            alpha = max(alpha, eval)
            if beta <= alpha:
                break
            visited_nodes += nodes
        return max_eval, best_move, visited_nodes

    else:
        min_eval = float('inf')
        best_move = None
        for move in possible_moves:
            game_copy = copy.deepcopy(game)
            game_copy.make_move(move[0], move[1])
            eval, some_move, nodes = alpha_beta(game_copy, depth - 1, heuristic, True, alpha, beta, 0)
            if eval < min_eval:
                min_eval = eval
                best_move = move
            beta = min(beta, eval)
            if beta <= alpha:
                break
            visited_nodes += nodes
        return min_eval, best_move, visited_nodes
