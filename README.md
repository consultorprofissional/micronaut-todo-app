# micronaut-todo-app

A Todo App build in micronaut framework running in kuberntes with jenkins-x
  
Watch pipeline activity via:  

    jx get activity -f micronaut-todo-app -w

Browse the pipeline log via:

    jx get build logs consultorprofissional/micronaut-todo-app/master

You can list the pipelines via: 

    jx get pipelines

When the pipeline is complete:

    jx get applications

To promotion to production environment run:

    jx promote --app micronaut-todo-app --version v0.0.3 --env production

This project using a custom build pack and you need to import then to your jx environment:

    jx edit buildpack -u https://github.com/consultorprofissional/jenkins-x-kubernetes.git -r master -b
