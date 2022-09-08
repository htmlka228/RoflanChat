window.scrollDownChat = function() {
    let chat = window.document.getElementsByClassName('roflan-messages-area')[0]

    if (chat) {
        const shouldScrollToBottom = chat.scrollHeight > chat.scrollTop + chat.clientHeight

        if (shouldScrollToBottom) {
            chat.scrollTop = chat.scrollHeight
        }
    }
}


