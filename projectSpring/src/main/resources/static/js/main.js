var input = document.querySelectorAll('input');
for (var i = 0; i < input.length; i++) {
    input[i].addEventListener('input', resizeInput);
}

for (var i = 0; i < input.length; i++) {
    resizeInput.call(input[i]);
}

function resizeInput() {
    this.style.width = this.value.length + "ch";
}

