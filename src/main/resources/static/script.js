document.getElementById('send-btn').addEventListener('click', function() {
    const userInput = document.getElementById('user-input').value;
    const chatBox = document.getElementById('chat-box');

    if (userInput.trim() !== '') {
        // Display user input in chat box
        const userMessage = document.createElement('div');
        userMessage.textContent = 'You: ' + userInput;
        chatBox.appendChild(userMessage);

        // Prepare the request
        fetch('/chat', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ content: userInput }) // Pass the userInput as an object with the key "content"
        })
            .then(response => response.text())
            .then(data => {
                // Display response in chat box
                const aiResponse = document.createElement('div');
                aiResponse.textContent = 'GPT: ' + data;
                chatBox.appendChild(aiResponse);
            })
            .catch(error => console.error('Error:', error));

        // Clear the text area
        document.getElementById('user-input').value = '';
    }
});
