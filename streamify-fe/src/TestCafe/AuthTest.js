import { Selector } from 'testcafe';
import { mockUserCredentials } from './userCredentials';

fixture `Login Page`
    .page `http://localhost:3000/login`;


test('Failed login', async t => {
    const submitButton = Selector('[data-test-id="submit-button"]');
    await t
        .typeText('#formUsername',"badUsername")
        .typeText('#formBasicPassword', "badPassword")
           
        .click(submitButton);
                
    await t
        .expect(Selector('.text-danger').withText('Username or password are incorrect').exists).ok();

});

test('Successful login', async t => {
    const submitButton = Selector('[data-test-id="submit-button"]');
    await t
        .typeText('#formUsername', mockUserCredentials.username)
        .typeText('#formBasicPassword', mockUserCredentials.password)
       
        .click(submitButton);       

    await t
        .expect(Selector('h1').withText('Recommended videos').exists).ok();
});

