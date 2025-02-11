from reversi import BOARD_SIZE

STATIC_WEIGHTS = [
    [4, -3, 2, 2, 2, 2, -3, 4],
    [-3, -4, -1, -1, -1, -1, -4, -3],
    [2, -1, 1, 0, 0, 1, -1, 2],
    [2, -1, 0, 1, 1, 0, -1, 2],
    [2, -1, 0, 1, 1, 0, -1, 2],
    [2, -1, 1, 0, 0, 1, -1, 2],
    [-3, -4, -1, -1, -1, -1, -4, -3],
    [4, -3, 2, 2, 2, 2, -3, 4]
]


# Heuristic that counts the difference between number of pieces
def difference_of_pieces(game):
    pieces = 0
    for row in range(BOARD_SIZE):
        for col in range(BOARD_SIZE):
            if game.board[row][col] == 1:  # positive for player 1
                pieces += 1
            elif game.board[row][col] == 2:  # negative for player 2
                pieces -= 1
    return pieces


# Heuristic that counts the weights of the fields where pieces are placed (using static weights)
def static_weights(game):
    score = 0
    for row in range(BOARD_SIZE):
        for col in range(BOARD_SIZE):
            if game.board[row][col] == 1:  # positive for player 1
                score += STATIC_WEIGHTS[row][col]
            elif game.board[row][col] == 2:  # negative for player 2
                score -= STATIC_WEIGHTS[row][col]
    return score


# Heuristic that combines two previous functions - sums the difference between number of pieces and weights
def pieces_and_weights(game):
    score = 0
    for row in range(BOARD_SIZE):
        for col in range(BOARD_SIZE):
            if game.board[row][col] == 1:  # positive for player 1
                score += 1
                score += STATIC_WEIGHTS[row][col]
            elif game.board[row][col] == 2:  # negative for player 2
                score -= 1
                score -= STATIC_WEIGHTS[row][col]
    return score


# Heuristic that counts the length of continuous lines of pieces
def continuous_lines(game):
    score = 0
    for row in range(BOARD_SIZE):
        for col in range(BOARD_SIZE):
            cur_field = game.board[row][col]
            # Check horizontal line
            if col > 0 and game.board[row][col - 1] == cur_field:
                score += (-2) * cur_field + 3  # positive for player 1, negative for player 2 (1 -> 1, 2 -> -1)
            if col < BOARD_SIZE - 1 and game.board[row][col + 1] == cur_field:
                score += (-2) * cur_field + 3
            # Check vertical line
            if row > 0 and game.board[row - 1][col] == cur_field:
                score += (-2) * cur_field + 3
            if row < BOARD_SIZE - 1 and game.board[row + 1][col] == cur_field:
                score += (-2) * cur_field + 3
            # Check diagonal lines
            if row > 0 and col > 0 and game.board[row - 1][col - 1] == cur_field:
                score += (-2) * cur_field + 3
            if row < BOARD_SIZE - 1 and col < BOARD_SIZE - 1 and game.board[row + 1][col + 1] == cur_field:
                score += (-2) * cur_field + 3
            if row > 0 and col < BOARD_SIZE - 1 and game.board[row - 1][col + 1] == cur_field:
                score += (-2) * cur_field + 3
            if row < BOARD_SIZE - 1 and col > 0 and game.board[row + 1][col - 1] == cur_field:
                score += (-2) * cur_field + 3
    return score
