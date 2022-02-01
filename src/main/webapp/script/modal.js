document.querySelector('#modal-container').style.visibility = 'visible';
document.getElementById('modal-close').onmouseup = function () {
    document.querySelector('#modal-container').style.visibility = 'hidden';
}