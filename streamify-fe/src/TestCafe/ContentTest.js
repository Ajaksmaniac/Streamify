
import { Selector } from 'testcafe';
import { mockUserCredentials } from './userCredentials';


fixture `Login Page`
    .page `http://localhost:3000/login`;

test('Create channel', async t => {
    const submitLoginButton = Selector('[data-test-id="submit-button"]');
    const createNewChannelButton = Selector('button').withText('Add new Channel');
    const submitNewChannelButton = Selector('button').withText('Submit');

    await t
        .typeText('#formUsername',mockUserCredentials.username)
        .typeText('#formBasicPassword', mockUserCredentials.password)
           
        .click(submitLoginButton);
                
        await t
        .expect(Selector('h1').withText('Recommended videos').exists).ok();

        await t
            .click(createNewChannelButton)
            .typeText('#channelName',"new channel")
            .click(submitNewChannelButton);
  
        await t
            .expect(Selector('h1').withText('Welcome to new channel').exists).ok();
});

test("Upload video", async t => {
    const submitLoginButton = Selector('[data-test-id="submit-button"]');

    await t
        .typeText('#formUsername',mockUserCredentials.username)
        .typeText('#formBasicPassword', mockUserCredentials.password)
        .click(submitLoginButton);

    await t
        .expect(Selector('h1').withText('Recommended videos').exists).ok();
        

    await t
        .click(Selector('[data-test-id="channelDropdown"]'))
        .click(Selector('[data-test-id="channelDropdownItem"]'))

    await t
        .click(Selector('[data-test-id="addNewVideoButton"]'))

        .typeText('#name', "newVideo")
        .typeText('#description', "description")
        .setFilesToUpload(Selector('input[type="file"]'), "../../../video-service/Files-Upload/1-first_video updated");
        
    await t
        .click(Selector('[data-test-id="uploadVideoButton"]'));

    await t
        .expect(Selector('h1').withText('Welcome to new channel').exists).ok();
})

test("Write comment", async t =>{
    const submitLoginButton = Selector('[data-test-id="submit-button"]');

    await t
        .typeText('#formUsername',mockUserCredentials.username)
        .typeText('#formBasicPassword', mockUserCredentials.password)
        .click(submitLoginButton);

    await t
        .expect(Selector('h1').withText('Recommended videos').exists).ok();
        
    await t
        .click(Selector('[data-test-id="channelDropdown"]'))
        .click(Selector('[data-test-id="channelDropdownItem"]'))

    await t.expect(Selector('h1').withText('Welcome to new channel').exists).ok();
        
    await t.click(Selector('[data-test-id="videoBox"]'));

    await t.typeText(Selector('[data-test-id="commentField"]'), "newly added comment")
    
    await t.click(Selector('[data-test-id="commentButton"]').withExactText("Add Comment"))
       
    await t.expect(Selector('p').withText('Comment Successfully posted').exists).ok();
    await t.expect(Selector('p').withText('newly added comment').exists).ok();

})

test("Delete comment", async t =>{
    const submitLoginButton = Selector('[data-test-id="submit-button"]');

    await t
        .typeText('#formUsername',mockUserCredentials.username)
        .typeText('#formBasicPassword', mockUserCredentials.password)
        .click(submitLoginButton);

    await t.expect(Selector('h1').withText('Recommended videos').exists).ok();
        
    await t
        .click(Selector('[data-test-id="channelDropdown"]'))
        .click(Selector('[data-test-id="channelDropdownItem"]'))

    await t.expect(Selector('h1').withText('Welcome to new channel').exists).ok();
        
    await t.click(Selector('[data-test-id="videoBox"]'));

    await t.click(Selector('[data-test-id="deleteCommentButton"]'))
       
    await t.expect(Selector('p').withText('Comment Successfully Deleted').exists).ok();
})

test("Delete video", async t => {
    const submitLoginButton = Selector('[data-test-id="submit-button"]');

    await t
        .typeText('#formUsername',mockUserCredentials.username)
        .typeText('#formBasicPassword', mockUserCredentials.password)
        .click(submitLoginButton);

    await t
        .expect(Selector('h1').withText('Recommended videos').exists).ok();
        

    await t
        .click(Selector('[data-test-id="channelDropdown"]'))
        .click(Selector('[data-test-id="channelDropdownItem"]'))

    await t
        .expect(Selector('h1').withText('Welcome to new channel').exists).ok();
        
    await t
        .click(Selector('[data-test-id="videoBox"]'));

    await t
        .click(Selector('[data-test-id="deleteVideoButton"]'));
    
})

test('Delete channel', async t => {
    const submitLoginButton = Selector('[data-test-id="submit-button"]');

    await t
        .typeText('#formUsername',mockUserCredentials.username)
        .typeText('#formBasicPassword', mockUserCredentials.password)
        .click(submitLoginButton);

    await t
        .expect(Selector('h1').withText('Recommended videos').exists).ok();

    await t
        .typeText('#formSearch', "channel")
        .click(Selector('[data-test-id="searchCheckBox"]'))
        .click(Selector('[data-test-id="searchButton"]'))
        .click(Selector('[data-test-id="channelBox"]'))

    await t
        .expect(Selector('h1').withText('Welcome to new channel').exists).ok();

    await t
        .click(Selector('[data-test-id="deleteChannelButton"]'))

    await t
        .expect(Selector('h1').withText('Recommended videos').exists).ok();
});


