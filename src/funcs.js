function bullsandcows(secret, guess) {
    let bulls = 0;
    let cows = 0;
    const secretArr = secret.split('');
    const guessArr = guess.split('');

    for (let i = 0; i < 4; i++) {
        if (secretArr[i] === guessArr[i]) {
            bulls++;
            secretArr[i] = null;
            guessArr[i] = null;
        }
    }
    
    for (let i = 0; i < 4; i++) {
        if (guessArr[i] !== null && secretArr.includes(guessArr[i])) {
            cows++;
            secretArr[secretArr.indexOf(guessArr[i])] = null;
        }
    }
    
    return { bulls, cows };
}

function hasUniqueDigits(number) {
    const digits = number.toString().split('');
    const digitSet = new Set(digits);
    
    return digitSet.size === digits.length;
}

function generateNumber() {
    let generated = 1000;
    while (hasUniqueDigits(generated) == false) {
        generated = Math.floor(Math.random() * 9000) + 1000;
    }
    
    return generated;
}