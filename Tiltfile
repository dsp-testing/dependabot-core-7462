load('ext://secret', 'secret_from_dict', 'secret_yaml_registry')
load('ext://helm_resource', 'helm_resource', 'helm_repo')
load('ext://uibutton', 'cmd_button', 'location', 'text_input')

namespace = "default"
release_name = "portail-formation"
backend_sources_path = "./backend"
docker_path = "./docker"

k8s_yaml(secret_from_dict(release_name + "-postgresql", inputs = {
    'postgres-password' : 'postgres',
    'password' : 'Ch@nge1t'
}))

docker_build('nerevahr/portail-formation-api:latest',
             context='.',
             # (Optional) Use a custom Dockerfile path
             dockerfile='docker/backend.Dockerfile',
             target="dev-mode",
             # (Optional) Filter the paths used in the build
             only=[backend_sources_path + '/src', backend_sources_path + '/pom.xml']
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
    './helm/portail-formation-backend',
    name=release_name,
    values=["./helm/portail-formation-backend/values-local.yaml"]
)
k8s_yaml(main_release)

# Install pgadmin4
helm_repo("runix", "https://helm.runix.net/", labels=["repositories"])
pgadmin_values = "./helm/tools/values-pgadmin.yaml"
helm_resource(
    'pgadmin4',
    'runix/pgadmin4',
    flags=["--values", pgadmin_values],
    labels=['tools'],
    port_forwards=[port_forward(9910, 80, name='console')]
)

k8s_resource(
    release_name + "-postgresql",
    new_name="database",
    labels=['backend'],
    port_forwards=['5432:5432']
)

k8s_resource(
    release_name + "-minio",
    new_name="s3",
    labels=['backend'],
    port_forwards=[port_forward(9001, 9001, name='console')]
)

app_port = '8090'
k8s_resource(
    release_name + "-api",
    new_name="api",
    labels=['backend'],
    port_forwards=[app_port + ':8080', '5005:5005'],
    links=[
        link('http://localhost:'+ app_port + '/api/files', 'files'),
        link('http://localhost:'+ app_port + '/api/modules', 'modules')
    ],
    resource_deps=['database']
)

cmd_button(name='helm-deps',
           argv=['helm', 'dependen cy', 'update', './helm/portail-formation-backend'],
           text='deps',
           location=location.NAV,
           icon_name='cloud_download')