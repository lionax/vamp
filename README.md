TU Darmstadt GDI Project WS 13/14
=================================

### GIT-HUB USAGE INSTRUCTIONS
1. sign up on github
2. send your usernames to mail@alexanderwellbrock.de
3. wait until u are signed in to the vamp repo
2. get git client
3. clone repo https://github.com/wellblogg/vamp/
4. create a branch with style 'vamp-[your-initials-here]' like 'vamp-aw'
5. learn github's flavored markdown: https://help.github.com/articles/github-flavored-markdown

### About GitHub
With github we can simply organize our code in a repo. So our code is save in the cloud and we don't have to fear that a load of jizz could reach the plugbar and destroy our data with a full-wipe.
Anyway, a repo is organized in branches. A branch is sort of a version tree. The master-branch holds the sum of the working code of eveyone of us. So everyone has his own branch to work with.
A pull is a sort of downloading and merging the changes from the webserver to the local machine. A push is the upstream version of it. Please only push your changes in your personal branch. The branches can be merged afterwards through client or web-client.
Besides the source-code control there is a neat tool to organize tasks, bugs and so on... It's like a social code network. You can simply create a task called issue and put information in it's description. Also pictures can be uploaded. Besides the issues there are also milestones that can be created and assigned with a dead line. Pretty cool to keep track of the progress.
There are some sorts of issue types like: bugs, todo, enhancment, question (pretty neat to keep track of quastions at work that can't be simply answered). Be sure to assign your issues to a milestone and a tag.
Why would we do this kind of extra work? It is really helpfull to recognize and remind after the project what you have done and to see what the others have done so far. Will be helpfull for the final tests.

### The Client
With the client you can simple clone the vamp repo with the link given above, as long as you are signed into the repo.
There is a official gui client for windows [under: http://windows.github.com/] but only a console app for linux. I'm using and recommending git-cola. Caution: I have to start git-cola gui with terminal, because of problems with authentication.

### Flavored Markdown
Is really powerfull and cool to style your comments and descriptions everywhere on github. Even lists and code-highlighting is possible. Here are some conventions that made it through experience. Be free to tell me about any ways of improvement.
#### Headings
Normaly i don't use the H1 heading often. Its way to big.

#### Comments of Issues
Place a H2 heading with really short description (like DONE, UPDATE), then a longer description maybe with a list or something, then a H5 Tag with the text 'Commit: [COMMIT NUMBER HERE]' (the commit number can be found on the commit page of your branch) and at last any pictures

#### Descriptions of Issues
No heading here (would be redundend), a long description of the problem or task, so it is easily to understand. If the problem is not big enough to split it up into two or more issues create a list with suptasks (cool thing about lists: they are interactive). Last one any pictures.

#### Milestones
Only a meaning name (taskname or something) here. No description needed.
