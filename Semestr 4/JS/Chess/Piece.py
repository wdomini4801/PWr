from Move import *
"""
This class represents a single chess piece of a specific color (black or white).
"""


class Piece:
    def __init__(self, color):
        self._color = color
        self._opposite_color = 'b' if self.color == 'w' else 'w'

    '''
    Hermetization
    '''
    @property
    def color(self):
        return self._color

    @property
    def opposite_color(self):
        return self._opposite_color

    '''
    Return all possible moves of a piece on a specific row and column (this contains also invalid moves that are eliminated later).
    '''
    def get_possible_moves(self, board, row, column, white_turn=None):
        return []


"""
This class represents Bishop - piece that can move diagonally ('sides' contains its directions (vectors)). Index is needed for
loading a proper image on a board.
"""


class Bishop(Piece):
    def __init__(self, color):
        super().__init__(color)
        self._index = 0
        self._sides = [(-1, -1), (1, 1), (1, -1), (-1, 1)]

    '''
    Hermetization
    '''
    @property
    def index(self):
        return self._index

    @property
    def sides(self):
        return self._sides

    '''
    Function returns all possible moves for a bishop.
    '''
    def get_possible_moves(self, board, row, column, white_turn=None):
        moves = []

        for side in self.sides:
            for d in range(1, 8):
                final_row = d * side[0] + row
                final_column = d * side[1] + column
                if 0 <= final_row <= 7 and 0 <= final_column <= 7:
                    piece = board.board[final_row][final_column]
                    if piece is None:
                        moves.append(Move(board.board, (row, column), (final_row, final_column)))
                    elif piece.color == self.color:
                        break
                    else:
                        moves.append(Move(board.board, (row, column), (final_row, final_column)))
                        break
                else:
                    break

        return moves


"""
This class represents King - piece that can move 1 square in each direction.
"""


class King(Piece):
    def __init__(self, color):
        super().__init__(color)
        self._index = 1
        self._row_sides = [-1, -1, -1, 0, 0, 1, 1, 1]
        self._column_sides = [-1, 0, 1, -1, 1, -1, 0, 1]

    '''
    Hermetization
    '''
    @property
    def index(self):
        return self._index

    @property
    def row_sides(self):
        return self._row_sides

    @property
    def column_sides(self):
        return self._column_sides

    '''
    Function returns all possible moves for a King.
    '''
    def get_possible_moves(self, board, row, column, white_turn=True):
        moves = []
        cur_color = 'w' if white_turn else 'b'
        for i in range(8):
            final_row = self.row_sides[i] + row
            final_column = self.column_sides[i] + column
            if 0 <= final_row <= 7 and 0 <= final_column <= 7:
                piece = board.board[final_row][final_column]
                if piece is None or piece.color != cur_color:
                    if cur_color == 'w':
                        board.white_king_pos = (final_row, final_column)
                    else:
                        board.black_king_pos = (final_row, final_column)
                    check, checks, pins = board.get_checks_and_pins(white_turn)
                    if not check:
                        moves.append(Move(board.board, (row, column), (final_row, final_column)))
                    if cur_color == 'w':
                        board.white_king_pos = (row, column)
                    else:
                        board.black_king_pos = (row, column)

        return moves


"""
This class represents Knight - piece that can move in 'L-style'.
"""


class Knight(Piece):
    def __init__(self, color):
        super().__init__(color)
        self._index = 2
        self._sides = [(-2, -1), (-2, 1), (-1, 2), (1, 2), (2, 1), (2, -1), (1, -2), (-1, -2)]

    '''
    Hermetization
    '''
    @property
    def index(self):
        return self._index

    @property
    def sides(self):
        return self._sides

    '''
    Function returns all possible moves for a Knight.
    '''
    def get_possible_moves(self, board, row, column, white_turn=None):
        moves = []
        pinned = False
        pin_side = ()

        for i in range(len(board.pins)-1, -1, -1):
            if board.pins[i][0] == row and board.pins[i][1] == column:
                pinned = True
                pin_side = (board.pins[i][2], board.pins[i][3])
                board.pins.remove(board.pins[i])
                break

        for side in self.sides:
            final_row = row + side[0]
            final_column = column + side[1]
            if 0 <= final_row <= 7 and 0 <= final_column <= 7:
                if not pinned:
                    piece = board.board[final_row][final_column]
                    if piece is None or piece.color == self.opposite_color:
                        moves.append(Move(board.board, (row, column), (final_row, final_column)))

        return moves


"""
This class represents Pawn - piece that can move 1 or once in a game - 2 squares forward and can capture 1 square forward diagonally
or enpassant.
"""


class Pawn(Piece):
    def __init__(self, color):
        super().__init__(color)
        self._index = 3

    '''
    Hermetization
    '''
    @property
    def index(self):
        return self._index

    '''
    Function returns all possible moves for a Pawn.
    '''
    def get_possible_moves(self, board, row, column, white_turn=None):
        moves = []
        pinned = False
        pin_side = ()

        for i in range(len(board.pins)-1, -1, -1):
            if board.pins[i][0] == row and board.pins[i][1] == column:
                pinned = True
                pin_side = (board.pins[i][2], board.pins[i][3])
                board.pins.remove(board.pins[i])
                break

        if self.color == 'w':
            begin_row = 6
            op_color = 'b'
            king_row, king_col = board.white_king_pos
            move_side = -1
        else:
            begin_row = 1
            op_color = 'w'
            king_row, king_col = board.black_king_pos
            move_side = 1

        if board.board[row + move_side][column] is None:
            if not pinned or pin_side == (move_side, 0):
                moves.append(Move(board.board, (row, column), (row + move_side, column)))
                if row == begin_row and board.board[row + 2*move_side][column] is None:
                    moves.append(Move(board.board, (row, column), (row + 2*move_side, column)))

        if column >= 1:
            if not pinned or pin_side == (move_side, -1):
                if board.board[row + move_side][column-1] is not None and board.board[row + move_side][column-1].color == op_color:
                        moves.append(Move(board.board, (row, column), (row + move_side, column-1)))
                if (row + move_side, column-1) == board.enpassant_square:
                    attack = block = False
                    if king_row == row:
                        if king_col < column:
                            inside = range(king_col+1, column-1)
                            outside = range(column+1, 8)
                        else:
                            inside = range(king_col-1, column, -1)
                            outside = range(column-2, -1, -1)
                        for i in inside:
                            if board.board[row][i] is not None:
                                block = True
                        for i in outside:
                            cur_piece = board.board[row][i]
                            if cur_piece is not None:
                                if cur_piece.color == op_color and (isinstance(cur_piece, Rook) or isinstance(cur_piece, Queen)):
                                    attack = True
                                else:
                                    block = True
                    if not attack or block:
                        moves.append(Move(board.board, (row, column), (row + move_side, column-1), enpassant=True))

        if column <= 6:
            if not pinned or pin_side == (move_side, 1):
                if board.board[row + move_side][column+1] is not None and board.board[row + move_side][column+1].color == op_color:
                        moves.append(Move(board.board, (row, column), (row + move_side, column+1)))
                if (row + move_side, column+1) == board.enpassant_square:
                    attack = block = False
                    if king_row == row:
                        if king_col < column:
                            inside = range(king_col+1, column)
                            outside = range(column+2, 8)
                        else:
                            inside = range(king_col-1, column+1, -1)
                            outside = range(column-1, -1, -1)
                        for i in inside:
                            if board.board[row][i] is not None:
                                block = True
                        for i in outside:
                            cur_piece = board.board[row][i]
                            if cur_piece is not None:
                                if cur_piece.color == op_color and (isinstance(cur_piece, Rook) or isinstance(cur_piece, Queen)):
                                    attack = True
                                else:
                                    block = True
                    if not attack or block:
                        moves.append(Move(board.board, (row, column), (row + move_side, column+1), enpassant=True))

        return moves


"""
This class represents Queen - piece that can move like Bishop and Rook.
"""


class Queen(Piece):
    def __init__(self, color):
        super().__init__(color)
        self._index = 4
        self._sides = [(-1, 0), (1, 0), (0, -1), (0, 1), (-1, -1), (1, 1), (1, -1), (-1, 1)]

    '''
    Hermetization
    '''
    @property
    def index(self):
        return self._index

    @property
    def sides(self):
        return self._sides

    '''
    Function returns all possible moves for a Queen.
    '''
    def get_possible_moves(self, board, row, column, white_turn=None):
        moves = []

        for side in self.sides:
            for d in range(1, 8):
                final_row = d * side[0] + row
                final_column = d * side[1] + column
                if 0 <= final_row <= 7 and 0 <= final_column <= 7:
                    piece = board.board[final_row][final_column]
                    if piece is None:
                        moves.append(Move(board.board, (row, column), (final_row, final_column)))
                    elif piece.color == self.color:
                        break
                    else:
                        moves.append(Move(board.board, (row, column), (final_row, final_column)))
                        break
                else:
                    break

        return moves


"""
This class represents Rook - piece that can move any squares horizontally or vertically.
"""


class Rook(Piece):
    def __init__(self, color):
        super().__init__(color)
        self._index = 5
        self._sides = [(-1, 0), (1, 0), (0, -1), (0, 1)]

    '''
    Hermetization
    '''
    @property
    def index(self):
        return self._index

    @property
    def sides(self):
        return self._sides

    '''
    Function returns all possible moves for a Rook.
    '''
    def get_possible_moves(self, board, row, column, white_turn=None):
        moves = []
        pinned = False
        pin_side = ()

        for i in range(len(board.pins)-1, -1, -1):
            if board.pins[i][0] == row and board.pins[i][1] == column:
                pinned = True
                pin_side = (board.pins[i][2], board.pins[i][3])
                if not isinstance(board.board[row][column], Queen):
                    board.pins.remove(board.pins[i])
                break

        for side in self.sides:
            for d in range(1, 8):
                final_row = d * side[0] + row
                final_column = d * side[1] + column
                if 0 <= final_row <= 7 and 0 <= final_column <= 7:
                    if not pinned or pin_side == side or pin_side == (-side[0], -side[1]):
                        piece = board.board[final_row][final_column]
                        if piece is None:
                            moves.append(Move(board.board, (row, column), (final_row, final_column)))
                        elif piece.color == self.color:
                            break
                        else:
                            moves.append(Move(board.board, (row, column), (final_row, final_column)))
                            break
                else:
                    break

        return moves
