from random import randint


def foo(length):
    generated = []
    while len(generated) < length:
        r = randint(1, length)
        if r not in generated:
            generated.append(r)

    return generated

generated = []

# recurve method
def foo2(length):
    if len(generated) < length:
        r = randint(1, length)
        if r not in generated:
            print(r)
            generated.append(r)
        foo2(length);

x = foo2(10)

