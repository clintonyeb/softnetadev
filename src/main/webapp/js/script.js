(function () {
    "use strict";

    var addingNewFeed = false;
    var removingFeed = false;

    window.onload = function () {
        main();
    };

    function main() {
        var feedModal = document.getElementById('feed-modal');
        if(feedModal){
            handleModal(feedModal);
        }
    }

    window.removeFeed = function removeFeed(feedId) {
        var removeFeedBtn = document.getElementById('remove-feed-btn')
        removeFeedBtn.classList.add('is-loading');

        deleteAjaxRequest('/delete_feed', feedId, function (err, data) {
            removeFeedBtn.classList.remove('is-loading');

            if (err) {
                console.log(err, 'err');
                return;
            }

            location.href = '/feeds';
        })
    }

    function handleModal(modal) {
        var feedModal = modal;
        var addFeedButton = document.getElementById('add-feed-btn');
        var feedModalSave = document.getElementById('feed-modal-save');
        var feedModalClose = document.getElementById('feed-modal-close');
        var feedModalCancel = document.getElementById('feed-modal-cancel');
        var modalBackground = document.getElementById('feed-modal-background');

        addFeedButton.addEventListener('click', function () {
            feedModal.classList.add('is-active');
        });

        [feedModalCancel, feedModalClose, modalBackground].forEach(function (el) {
            el.addEventListener('click', closeModal);
        })

        feedModalSave.addEventListener('click', addRSSFeed);
    }

    function closeModal() {
        if(addingNewFeed) return false;
        var feedModal = document.getElementById('feed-modal');
        feedModal.classList.remove('is-active');
    }

    function addRSSFeed() {
        if(addingNewFeed) return false;

        var urlEl = document.getElementById('feed-url');
        var nameEl = document.getElementById('feed-name');
        var feedModalSave = document.getElementById('feed-modal-save');
        var notification = document.getElementById('notification');

        // reset form
        resetForm();

        var url = urlEl.value;
        var name = nameEl.value;

        // validate url field

        if (!_isRequired(url)) {
            urlEl.classList.add('is-danger');
            urlEl.focus();
            showError('URL field is required')
            return false;
        }

        if (!_isUrl(url)) {
            urlEl.classList.add('is-danger');
            urlEl.focus();
            showError('URL is invalid');
            return false;
        }


        if (!_isRequired(name)) {
            nameEl.classList.add('is-danger');
            nameEl.focus();
            showError('Name field is required')
            return false;
        }

        // All validations passed

        var data = {
            url: url,
            feed_name: name
        }

        feedModalSave.classList.add('is-loading');
        addingNewFeed = true;

        postAjaxRequest('/feeds', data, function (err, data) {
            addingNewFeed = false;

            if (err) {
                console.log(err, 'err');
                return;
            }

            feedModalSave.classList.remove('is-loading');

            resetForm();
            urlEl.value = ''
            nameEl.value = '';
            closeModal();
            window.location.reload(); // refresh page
        })

        function resetForm() {
            urlEl.classList.remove('is-danger');
            nameEl.classList.remove('is-danger');
            notification.classList.add('is-hidden');
        }

    }

    function showError(mess) {
        var messageEl = document.getElementById('message-content');
        var notification = document.getElementById('notification');

        messageEl.textContent = mess;
        notification.classList.remove('is-hidden');
    }

    function _isRequired(value) {
        if (Array.isArray(value)) return !!value.length
        if (value === undefined || value === null) {
            return false
        }

        if (value === false) {
            return true
        }

        if (value instanceof Date) {
            // invalid date won't pass
            return !isNaN(value.getTime())
        }

        if (typeof value === 'object') {
            for (let _ in value) return true
            return false
        }

        return !!String(value).length
    }

    function _isUrl(value) {
        var regex = /^(?:(?:https?|ftp):\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:[/?#]\S*)?$/i
        return regex.test(value);
    }

    function postAjaxRequest(url, data, callback) {
        var httpRequest = new XMLHttpRequest();

        httpRequest.open('POST', url);
        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        httpRequest.send('url=' + encodeURIComponent(data.url) + '&feed_name=' + encodeURIComponent(data.feed_name));

        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState === XMLHttpRequest.DONE) {
                if (httpRequest.status === 200) {
                    return callback(null, httpRequest.responseText);
                } else {
                    return callback(new Error('Error saving feed'));
                }
            }
        }
    }

    function deleteAjaxRequest(url, id, callback) {
        var httpRequest = new XMLHttpRequest();

        httpRequest.open('POST', url);
        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        httpRequest.send('feed_id=' + encodeURIComponent(id))

        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState === XMLHttpRequest.DONE) {
                if (httpRequest.status === 200) {
                    return callback(null, httpRequest.responseText);
                } else {
                    return callback(new Error('Error saving feed'));
                }
            }
        }
    }

    function feedItemClicked(feedId) {
        window.location.href = "/messages?feed_id=" + feedId;
    }
})();