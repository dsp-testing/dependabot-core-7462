
docker_build('my-app:latest',
             context='.',
             # (Optional) Use a custom Dockerfile path
             dockerfile='./Dockerfile',
             target="dev-mode",
             # (Optional) Filter the paths used in the build
             only=['./src', 'pom.xml']
             # (Recommended) Updating a running container in-place
             # https://docs.tilt.dev/live_update_reference.html
            #  live_update=[
            #     # Sync files from host to container
            #     sync('./app', '/src/'),
            #     # Execute commands inside the container when certain
            #     # paths change
            #     run('/src/codegen.sh', trigger=['./app/api'])
            #  ]
)

k8s_yaml(helm(
    './helm/portail-formation-demo',
    name='my-app'
))


k8s_resource("my-app-portail-formation-demo", new_name="backend", port_forwards=['8090:8080', '5005:5005'])