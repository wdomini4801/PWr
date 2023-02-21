class Console:

    @staticmethod
    def get_string(message, name="string", default=None,
                   minimum_length=0, maximum_length=800,
                   force_lower=False):
        message += ": " if default is None else " [{0}]: ".format(default)
        while True:
            try:
                line = input(message)
                if not line:
                    if default is not None:
                        return default
                    if minimum_length == 0:
                        return ""
                    else:
                        raise ValueError("{0} may not be empty".format(
                            name))
                if not (minimum_length <= len(line) <= maximum_length):
                    raise ValueError("{0} must have at least {1} and "
                                     "at most {2} characters".format(name, minimum_length, maximum_length))
                return line if not force_lower else line.lower()
            except ValueError as err:
                print("ERROR", err)
