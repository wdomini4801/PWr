

import pygame as pg
import os
import copy
from Piece import *

HEIGHT = WIDTH = 512
AMOUNT_OF_SQUARES = 8
SIZE_OF_SQUARE = HEIGHT // AMOUNT_OF_SQUARES

bB = pg.image.load(os.path.join("images", "bB.png"))
bK = pg.image.load(os.path.join("images", "bK.png"))
bN = pg.image.load(os.path.join("images", "bN.png"))
bp = pg.image.load(os.path.join("images", "bp.png"))
bQ = pg.image.load(os.path.join("images", "bQ.png"))
bR = pg.image.load(os.path.join("images", "bR.png"))

wB = pg.image.load(os.path.join("images", "wB.png"))
wK = pg.image.load(os.path.join("images", "wK.png"))
wN = pg.image.load(os.path.join("images", "wN.png"))
wp = pg.image.load(os.path.join("images", "wp.png"))
wQ = pg.image.load(os.path.join("images", "wQ.png"))
wR = pg.image.load(os.path.join("images", "wR.png"))

black_pieces = [bB, bK, bN, bp, bQ, bR]
white_pieces = [wB, wK, wN, wp, wQ, wR]


for piece in black_pieces:
    pg.transform.scale(piece, (SIZE_OF_SQUARE, SIZE_OF_SQUARE))

'''
This class represents a state of a chess board and is responsible for drawing board and loading piece images.
'''


class Board:
    def __init__(self):
        self._board = [[None for x in range(AMOUNT_OF_SQUARES)] for _ in range(AMOUNT_OF_SQUARES)]
        self._white_king_pos = ()
        self._black_king_pos = ()
        self._check = False
        self._game_code = 0  # 0 - game not over, 1 - checkmate, -1 - stalemate
        self._checks = []
        self._pins = []
        self._enpassant_square = ()
        self._castling_sides = CastlingSides(True, True, True, True)
        self._castling_list = [copy.deepcopy(self.castling_sides)]
        self._initialize_board()

    '''
    Hermetization
    '''
    @property
    def board(self):
        return self._board

    @property
    def white_king_pos(self):
        return self._white_king_pos

    @property
    def black_king_pos(self):
        return self._black_king_pos

    @property
    def check(self):
        return self._check

    @property
    def game_code(self):
        return self._game_code

    @property
    def checks(self):
        return self._checks

    @property
    def pins(self):
        return self._pins

    @property
    def enpassant_square(self):
        return self._enpassant_square

    @property
    def castling_sides(self):
        return self._castling_sides

    @property
    def castling_list(self):
        return self._castling_list

    @white_king_pos.setter
    def white_king_pos(self, pos):
        if 0 <= pos[0] <= 7 and 0 <= pos[1] <= 7:
            self._white_king_pos = pos

    @black_king_pos.setter
    def black_king_pos(self, pos):
        if 0 <= pos[0] <= 7 and 0 <= pos[1] <= 7:
            self._black_king_pos = pos

    @check.setter
    def check(self, check):
        if isinstance(check, bool):
            self._check = check

    @checks.setter
    def checks(self, checks):
        if isinstance(checks, list):
            self._checks = checks

    @pins.setter
    def pins(self, pins):
        if isinstance(pins, list):
            self._pins = pins

    @enpassant_square.setter
    def enpassant_square(self, square):
        if isinstance(square, tuple):
            self._enpassant_square = square

    @castling_sides.setter
    def castling_sides(self, sides):
        if isinstance(sides, CastlingSides):
            self._castling_sides = sides

    @game_code.setter
    def game_code(self, code):
        if isinstance(code, int) and code in [-1, 0, 1]:
            self._game_code = code

    '''
    This Method sets positions of pieces at the beginning state of the game.
    '''
    def _initialize_board(self):
        self.white_king_pos = (7, 4)
        self.black_king_pos = (0, 4)

        # black side
        self.board[0][0] = Rook('b')
        self.board[0][1] = Knight('b')
        self.board[0][2] = Bishop('b')
        self.board[0][3] = Queen('b')
        self.board[0][4] = King('b')
        self.board[0][5] = Bishop('b')
        self.board[0][6] = Knight('b')
        self.board[0][7] = Rook('b')

        self.board[1][0] = Pawn('b')
        self.board[1][1] = Pawn('b')
        self.board[1][2] = Pawn('b')
        self.board[1][3] = Pawn('b')
        self.board[1][4] = Pawn('b')
        self.board[1][5] = Pawn('b')
        self.board[1][6] = Pawn('b')
        self.board[1][7] = Pawn('b')

        # white site
        self.board[6][0] = Pawn('w')
        self.board[6][1] = Pawn('w')
        self.board[6][2] = Pawn('w')
        self.board[6][3] = Pawn('w')
        self.board[6][4] = Pawn('w')
        self.board[6][5] = Pawn('w')
        self.board[6][6] = Pawn('w')
        self.board[6][7] = Pawn('w')

        self.board[7][0] = Rook('w')
        self.board[7][1] = Knight('w')
        self.board[7][2] = Bishop('w')
        self.board[7][3] = Queen('w')
        self.board[7][4] = King('w')
        self.board[7][5] = Bishop('w')
        self.board[7][6] = Knight('w')
        self.board[7][7] = Rook('w')

    '''
    This Method creates a game view of the board (pieces).
    '''
    def draw_board(self, win):
        for row in range(AMOUNT_OF_SQUARES):
            for column in range(AMOUNT_OF_SQUARES):
                current_piece = self.board[row][column]
                if current_piece is not None:
                    if current_piece.color == 'b':
                        win.blit(black_pieces[current_piece.index], pg.Rect(column*SIZE_OF_SQUARE, row*SIZE_OF_SQUARE,
                                                                            SIZE_OF_SQUARE, SIZE_OF_SQUARE))
                    elif current_piece.color == 'w':
                        win.blit(white_pieces[current_piece.index], pg.Rect(column*SIZE_OF_SQUARE, row*SIZE_OF_SQUARE,
                                         SIZE_OF_SQUARE, SIZE_OF_SQUARE))

    '''
    This Method returns all possible moves in a current state of the game (depends on whose turn it is).
    '''
    def get_possible_moves(self, white_turn=True):
        moves = []
        for row in range(len(self._board)):
            for column in range(len(self.board[row])):
                piece = self.board[row][column]
                if piece is not None:
                    color = self._board[row][column].color
                    if (color == 'b' and not white_turn) or (color == 'w' and white_turn):
                        moves.extend(piece.get_possible_moves(self, row, column, white_turn))
        return moves

    '''
    Method returns all correct moves in a current state of the game. It accepts all possible moves and checks if they 
    are correct (depends on checks, pins, castles, enpassant moves). 
    '''
    def get_correct_moves(self, white_turn=True):
        self.check, self.checks, self.pins = self.get_checks_and_pins(white_turn)
        king_pos = self.white_king_pos if white_turn else self.black_king_pos
        if self.check:
            if len(self.checks) == 1:
                moves = self.get_possible_moves(white_turn)
                current_check = self.checks[0]
                checking_piece = self.board[current_check[0]][current_check[1]]
                correct_squares = []
                if isinstance(checking_piece, Knight):
                    correct_squares = [(current_check[0], current_check[1])]
                else:
                    for i in range(1, 8):
                        correct_square = (current_check[2] * i + king_pos[0], current_check[3] * i + king_pos[1])
                        correct_squares.append(correct_square)
                        if correct_square[0] == current_check[0] and correct_square[1] == current_check[1]:
                            break

                for i in range(len(moves)-1, -1, -1):
                    if not isinstance(moves[i].moved_piece, King):
                        if not (moves[i].end[0], moves[i].end[1]) in correct_squares:
                            moves.remove(moves[i])
            else:
                moves = self.board[king_pos[0]][king_pos[1]].get_possible_moves(self, king_pos[0], king_pos[1], white_turn)
        else:
            moves = self.get_possible_moves(white_turn)
            if white_turn:
                moves.extend(self.get_castling_moves(white_turn))
            else:
                moves.extend(self.get_castling_moves(white_turn))

        if len(moves) == 0:
            self.game_code = 1 if self.check else -1

        return moves

    '''
    Method returns all checks and pins at a current state of the board (depends on whose turn it is).
    '''
    def get_checks_and_pins(self, white_turn=True):
        checks = []
        pins = []
        check = False

        if white_turn:
            color = 'w'
            opp_color = 'b'
            row = self.white_king_pos[0]
            column = self.white_king_pos[1]
        else:
            color = 'b'
            opp_color = 'w'
            row = self.black_king_pos[0]
            column = self.black_king_pos[1]

        sides = [(-1, 0), (0, -1), (1, 0), (0, 1), (-1, -1), (-1, 1), (1, -1), (1, 1)]

        for j in range(len(sides)):
            side = sides[j]
            pin = ()
            for i in range(1, 8):
                final_row = side[0] * i + row
                final_column = side[1] * i + column
                if 0 <= final_row <= 7 and 0 <= final_column <= 7:
                    piece = self.board[final_row][final_column]
                    if piece is not None:
                        if piece.color == color and not isinstance(piece, King):
                            if pin == ():
                                pin = (final_row, final_column, side[0], side[1])
                            else:
                                break
                        elif piece.color == opp_color:
                            if (isinstance(piece, Rook) and 0 <= j <= 3) or (isinstance(piece, Bishop) and 4 <= j <= 7) \
                                    or (isinstance(piece, Pawn) and i == 1 and (
                                    (opp_color == 'w' and 6 <= j <= 7) or (opp_color == 'b' and 4 <= j <= 5))) \
                                    or (isinstance(piece, Queen)) or (isinstance(piece, King) and i == 1):
                                if pin == ():
                                    check = True
                                    checks.append((final_row, final_column, side[0], side[1]))
                                    break
                                else:
                                    pins.append(pin)
                                    break
                            else:
                                break
                else:
                    break

        sides = [(-2, -1), (-2, 1), (-1, 2), (1, 2), (2, 1), (2, -1), (1, -2), (-1, -2)]

        for side in sides:
            final_row = row + side[0]
            final_column = column + side[1]
            if 0 <= final_row <= 7 and 0 <= final_column <= 7:
                piece = self.board[final_row][final_column]
                if isinstance(piece, Knight) and piece.color == opp_color:
                    check = True
                    checks.append((final_row, final_column, side[0], side[1]))

        return check, checks, pins

    '''
    Method returns all castling moves at a current state of the board (depends on whose turn it is).
    '''
    def get_castling_moves(self, white_turn=True):
        moves = []
        if not self.check:
            if (white_turn and self.castling_sides.wks) or (not white_turn and self.castling_sides.bks):
                moves.extend(self._get_kingside_castling_moves(white_turn))
            if (white_turn and self.castling_sides.wqs) or (not white_turn and self.castling_sides.bqs):
                moves.extend(self._get_queenside_castling_moves(white_turn))
        return moves

    '''
    Method returns all kingside castling moves.
    '''
    def _get_kingside_castling_moves(self, white_turn=True):
        moves = []
        king_row = self.white_king_pos[0] if white_turn else self.black_king_pos[0]
        king_col = self.white_king_pos[1] if white_turn else self.black_king_pos[1]

        if self.board[king_row][king_col+1] is None and self.board[king_row][king_col+2] is None:
            if not self.attacked_square(king_row, king_col+1, white_turn) and not \
                    self.attacked_square(king_row, king_col+2, white_turn):
                moves.append(Move(self.board, (king_row, king_col), (king_row, king_col+2), castle=True))
        return moves

    '''
    Method returns all queenside castling moves.
    '''
    def _get_queenside_castling_moves(self, white_turn=True):
        moves = []
        king_row = self.white_king_pos[0] if white_turn else self.black_king_pos[0]
        king_col = self.white_king_pos[1] if white_turn else self.black_king_pos[1]

        if self.board[king_row][king_col - 1] is None and self.board[king_row][king_col - 2] is None and self.board[king_row][king_col - 3] is None:
            if not self.attacked_square(king_row, king_col - 1, white_turn) and not \
                    self.attacked_square(king_row, king_col - 2, white_turn):
                moves.append(Move(self.board, (king_row, king_col), (king_row, king_col - 2), castle=True))
        return moves

    '''
    Method checks if a square is attacked by enemy piece.
    '''
    def attacked_square(self, row, column, white_turn=True):
        moves = self.get_possible_moves(not white_turn)
        for move in moves:
            if move.end[0] == row and move.end[1] == column:
                return True
        return False
