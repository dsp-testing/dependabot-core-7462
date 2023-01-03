load('ext://secret', 'secret_from_dict', 'secret_yaml_registry')
load('ext://helm_resource', 'helm_resource', 'helm_repo')
load('ext://uibutton', 'cmd_button', 'location', 'text_input')

docker_build('nerevahr/portail-formation-demo:latest',
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

main_release = helm(
    './helm/portail-formation-demo',
    name='my-app'
)
k8s_yaml(main_release)

# Install pgadmin4
helm_repo("runix", "https://helm.runix.net/")
helm_resource(
    'pgadmin4',
    'runix/pgadmin4',
    flags=["--values", "./helm/tools/values-pgadmin.yaml"],
    labels=['tools'],
    port_forwards=[port_forward(9910, 80, name='console')]
)

k8s_resource(
    "my-app-postgresql",
    new_name="database",
    labels=['backend'],
    port_forwards=['5432:5432']
)

k8s_resource(
    "my-app-minio",
    new_name="s3",
    labels=['backend'],
    port_forwards=[port_forward(9001, 9001, name='console')]
)

app_port = '8090'
k8s_resource(
    "my-app-portail-formation-demo",
    new_name="api",
    labels=['backend'],
    port_forwards=[app_port + ':8080', '5005:5005'],
    links=[link('http://localhost:'+ app_port + '/api/files', 'files')],
    resource_deps=['database']
)

cmd_button(name='helm-deps',
           argv=['helm', 'dependency', 'update', './helm/portail-formation-demo'],
           text='deps',
           location=location.NAV,
           icon_name='cloud_download')