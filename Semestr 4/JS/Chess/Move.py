"""
This class represents move on a board starting from square 'begin', ending on square 'end'. It also stores information about
moved and captured piece, and also if a move is kind of enpassant move or castle move.
"""


class Move:
    ranks_to_rows = {"1": 7, "2": 6, "3": 5, "4": 4,
                     "5": 3, "6": 2, "7": 1, "8": 0}
    rows_to_ranks = {v: k for k, v in ranks_to_rows.items()}
    files_to_cols = {"a": 0, "b": 1, "c": 2, "d": 3,
                     "e": 4, "f": 5, "g": 6, "h": 7}
    cols_to_files = {v: k for k, v in files_to_cols.items()}

    def __init__(self, board, begin, end, promotion=False, enpassant=False, castle=False):
        self._begin = begin
        self._end = end
        self._captured_piece = board[self._end[0]][self._end[1]]
        self._moved_piece = board[self._begin[0]][self._begin[1]]
        self._promotion = promotion
        self._enpassant = enpassant
        self._castle = castle

    '''
    Hermetization
    '''
    @property
    def begin(self):
        return self._begin

    @property
    def end(self):
        return self._end

    @property
    def captured_piece(self):
        return self._captured_piece

    @property
    def moved_piece(self):
        return self._moved_piece

    @property
    def promotion(self):
        return self._promotion

    @property
    def enpassant(self):
        return self._enpassant

    @property
    def castle(self):
        return self._castle

    @enpassant.setter
    def enpassant(self, enpassant):
        if isinstance(enpassant, bool):
            self._enpassant = enpassant

    @castle.setter
    def castle(self, castle):
        if isinstance(castle, bool):
            self._castle = castle

    '''
    Overriding the 'equals' method, to compare moves with those which are possible in a current game status.
    '''
    def __eq__(self, other):
        if isinstance(other, Move):
            return self.begin[0] == other.begin[0] and self.begin[1] == other.begin[1] and self.end[0] == other.end[0] and self.end[1] == other.end[1]
        return False

    '''
    Return a chess notation of the move.
    '''
    def get_rank_file(self, row, col):
        return self.cols_to_files[col] + self.rows_to_ranks[row]

    '''
    Return a 'square-like' notation of the move (it is needed for integrating with chess engine).
    '''
    def __str__(self):
        return self.get_rank_file(self.begin[0], self.begin[1]) + self.get_rank_file(self.end[0], self.end[1])


'''
This class represents a tuple that contains current castling possibilities on a board (white kingside, black kingside,
white queenside, black queenside).
'''


class CastlingSides:
    def __init__(self, wks, bks, wqs, bqs):
        self._wks = wks
        self._bks = bks
        self._wqs = wqs
        self._bqs = bqs

    '''
    Hermetization
    '''
    @property
    def wks(self):
        return self._wks

    @property
    def bks(self):
        return self._bks

    @property
    def wqs(self):
        return self._wqs

    @property
    def bqs(self):
        return self._bqs

    @wks.setter
    def wks(self, wks):
        if isinstance(wks, bool):
            self._wks = wks

    @bks.setter
    def bks(self, bks):
        if isinstance(bks, bool):
            self._wks = bks

    @wqs.setter
    def wqs(self, wqs):
        if isinstance(wqs, bool):
            self._wqs = wqs

    @bqs.setter
    def bqs(self, bqs):
        if isinstance(bqs, bool):
            self._bqs = bqs

    def __str__(self):
        return '{0}, {1}, {2}, {3}'.format(self.wks, self.bks, self.wqs, self.bqs)
