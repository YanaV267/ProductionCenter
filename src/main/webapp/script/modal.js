document.querySelector('#modal-close').classList.remove('closeHide');
document.querySelector('#modal-close').classList.add('close');
document.querySelector('#modal-container').style.visibility = 'visible';
document.getElementById('modal-close').onmouseup = function () {
    document.querySelector('#modal-close').classList.remove('close');
    document.querySelector('#modal-close').classList.add('closeHide');
    document.querySelector('#modal-container').style.visibility = 'hidden';
}