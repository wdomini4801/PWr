from Board import *
from Piece import *
from Move import *

'''
Class represents current state of the game - board, list of moves made and whose turn it is. It is also responsible for making
and undoing moves.
'''


class GameStatus:
    def __init__(self):
        self._board = Board()
        self._moves_list = []
        self._move_log = []
        self._white_turn = True

    '''
    Hermetization
    '''

    @property
    def board(self):
        return self._board

    @property
    def moves_list(self):
        return self._moves_list

    @property
    def move_log(self):
        return self._move_log

    @property
    def white_turn(self):
        return self._white_turn

    @white_turn.setter
    def white_turn(self, turn):
        if isinstance(turn, bool):
            self._white_turn = turn

    '''
    This method is responsible for making moves - it changes the state of the board and updates the moves list.
    '''
    def make_move(self, move):
        if self.board.board[move.begin[0]][move.begin[1]] is not None:
            self.board.board[move.begin[0]][move.begin[1]] = None
            self.board.board[move.end[0]][move.end[1]] = move.moved_piece
            self.moves_list.append(move)
            self.move_log.append(str(move))
            self.white_turn = not self.white_turn

            self._update_king_location(move)

            if isinstance(move.moved_piece, Pawn) and (move.moved_piece.color == 'w' and move.end[0] == 0 or
                            move.moved_piece.color == 'b' and move.end[0] == 7):
                move.promotion = True
                self.board.board[move.end[0]][move.end[1]] = Queen(move.moved_piece.color)

            if move.enpassant:
                self.board.board[move.begin[0]][move.end[1]] = None

            if isinstance(move.moved_piece, Pawn) and abs(move.begin[0]-move.end[0]) == 2:
                self.board.enpassant_square = ((move.begin[0] + move.end[0])//2, move.begin[1])
            else:
                self.board.enpassant_square = ()

            if move.castle:
                if move.end[1] - move.begin[1] == 2:
                    self.board.board[move.end[0]][move.end[1]-1] = self.board.board[move.end[0]][move.end[1]+1]
                    self.board.board[move.end[0]][move.end[1]+1] = None
                else:
                    self.board.board[move.end[0]][move.end[1]+1] = self.board.board[move.end[0]][move.end[1]-2]
                    self.board.board[move.end[0]][move.end[1]-2] = None

            self._update_castling_sides(move)
            self.board.castling_list.append(copy.deepcopy(self.board.castling_sides))

    '''
    This method is responsible for undoing moves.
    '''
    def take_back(self):
        if len(self.moves_list) != 0:
            move = self.moves_list.pop()
            self.move_log.pop()
            self.board.board[move.end[0]][move.end[1]] = move.captured_piece
            self.board.board[move.begin[0]][move.begin[1]] = move.moved_piece
            self.white_turn = not self.white_turn

            self._update_king_location(move, takeback=True)

            if move.enpassant:
                self.board.board[move.end[0]][move.end[1]] = None
                self.board.board[move.begin[0]][move.end[1]] = Pawn('b') if self.white_turn else Pawn('w')
                self.board.enpassant_square = (move.end[0], move.end[1])

            if isinstance(move.moved_piece, Pawn) and abs(move.begin[0]-move.end[0]) == 2:
                self.board.enpassant_square = ()

            self.board.castling_list.pop()
            self.board.castling_sides = self.board.castling_list[-1]

            if move.castle:
                if move.end[1] - move.begin[1] == 2:
                    self.board.board[move.end[0]][move.end[1]+1] = self.board.board[move.end[0]][move.end[1]-1]
                    self.board.board[move.end[0]][move.end[1]-1] = None
                else:
                    self.board.board[move.end[0]][move.end[1]-2] = self.board.board[move.end[0]][move.end[1]+1]
                    self.board.board[move.end[0]][move.end[1]+1] = None

    '''
    This method updates the king's location on the board (it is important because king has very many constraints that have 
    to be checked while making a move).
    '''
    def _update_king_location(self, move, takeback=False):
        if isinstance(move.moved_piece, King):
            if move.moved_piece.color == 'w':
                if takeback:
                    self.board.white_king_pos = (move.begin[0], move.begin[1])
                else:
                    self.board.white_king_pos = (move.end[0], move.end[1])
            elif move.moved_piece.color == 'b':
                if takeback:
                    self.board.black_king_pos = (move.begin[0], move.begin[1])
                else:
                    self.board.black_king_pos = (move.end[0], move.end[1])

    '''
    This method updates current castling possibilities (depends on if the king or rook was moved).
    '''
    def _update_castling_sides(self, move):
        if isinstance(move.moved_piece, King):
            if move.moved_piece.color == 'w':
                self.board.castling_sides.wks = False
                self.board.castling_sides.wqs = False
            else:
                self.board.castling_sides.bks = False
                self.board.castling_sides.bqs = False
        elif isinstance(move.moved_piece, Rook):
            if move.moved_piece.color == 'w':
                if move.begin[0] == 7:
                    if move.begin[1] == 0:
                        self.board.castling_sides.wqs = False
                    elif move.begin[1] == 7:
                        self.board.castling_sides.wks = False
            else:
                if move.begin[0] == 0:
                    if move.begin[1] == 0:
                        self.board.castling_sides.bqs = False
                    elif move.begin[1] == 7:
                        self.board.castling_sides.bks = False
